package com.capstone.mymentor.ui.questions

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.mymentor.MainActivity
import com.capstone.mymentor.R
import com.capstone.mymentor.models.MenteePosition
import com.capstone.mymentor.models.MentorPosition
import com.capstone.mymentor.models.Personality
import com.capstone.mymentor.models.Roles
import com.capstone.mymentor.models.YearsPosition
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class QuestionsViewModel : ViewModel() {

    private val questionOrder: List<Questions> = listOf(
        Questions.ROLE,
        Questions.PERSONALITY,
        Questions.POSITION,
        Questions.YEARS,
    )

    private var questionIndex = 0

    private val _roleResponse = mutableStateOf<Roles?>(null)
    val roleResponse: Roles?
        get() = _roleResponse.value

    private val _personalityResponse = mutableStateOf<Personality?>(null)
    val personalityResponse: Personality?
        get() = _personalityResponse.value

    private val _positionResponse = mutableStateOf<MenteePosition?>(null)
    val positionResponse: MenteePosition?
        get() = _positionResponse.value

    private val _yearsResponse = mutableStateOf<YearsPosition?>(null)
    val yearsResponse: YearsPosition?
        get() = _yearsResponse.value

    private val _mentorPositionResponse = mutableStateOf<MentorPosition?>(null)
    val mentorPositionResponse: MentorPosition?
        get() = _mentorPositionResponse.value

    private val _questionScreenData = mutableStateOf(createQuestionScreenData())
    val questionScreenData: QuestionScreenData
        get() = _questionScreenData.value

    private val _isNextEnabled = mutableStateOf(false)
    val isNextEnabled: Boolean
        get() = _isNextEnabled.value

    fun onBackPressed(): Boolean {
        if (questionIndex == 0) {
            return false
        }
        changeQuestion(questionIndex - 1)
        return true
    }

    fun onPreviousPressed() {
        if (questionIndex == 0) {
            throw IllegalStateException("onPreviousPressed when on question 0")
        }
        changeQuestion(questionIndex - 1)
    }

    fun onNextPressed() {
        changeQuestion(questionIndex + 1)
    }

    fun onDonePressed(context: Context) {

        val response = hashMapOf<String, String>()

        response["Role"] = _roleResponse.value?.let { context.getString(it.stringResourceId) } ?: ""
        response["Personality"] =
            _personalityResponse.value?.let { context.getString(it.stringResourceId) } ?: ""
        response["Position"] =
            _positionResponse.value?.let { context.getString(it.stringResourceId) }
                ?: _mentorPositionResponse.value?.let { context.getString(it.stringResourceId) }
                        ?: ""
        response["Years of Experience"] =
            (if (_roleResponse.value == Roles(R.string.mentee, R.drawable.mentee)) {
                "Mentee"
            } else {
                _yearsResponse.value?.let { context.getString(it.stringResourceId) }
            }).toString()

        val user = Firebase.auth.currentUser
        val email = user?.email.toString()
        val firebase = FirebaseFirestore.getInstance()
        firebase.collection("User Responses").document(email).set(response)
            .addOnSuccessListener {
                navigateToMainActivity(context)
            }
            .addOnFailureListener { exception ->
                Log.d("Questions onDonePressed: ", exception.message.toString())
                Toast.makeText(
                    context,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
    }

    private fun navigateToMainActivity(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    private fun changeQuestion(newQuestionIndex: Int) {
        questionIndex = newQuestionIndex
        _isNextEnabled.value = getIsNextEnabled()
        _questionScreenData.value = createQuestionScreenData()
    }

    fun onRoleResponse(role: Roles) {
        _roleResponse.value = role
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onPersonalityResponse(personality: Personality) {
        _personalityResponse.value = personality
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onPositionResponse(position: MenteePosition) {
        _positionResponse.value = position
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onMentorPositionResponse(position: MentorPosition) {
        _mentorPositionResponse.value = position
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onYearsResponse(position: YearsPosition) {
        _yearsResponse.value = position
        _isNextEnabled.value = getIsNextEnabled()
    }

    private fun getIsNextEnabled(): Boolean {
        return when (questionOrder[questionIndex]) {
            Questions.ROLE -> _roleResponse.value != null
            Questions.PERSONALITY -> _personalityResponse.value != null
            Questions.POSITION -> {
                if (roleResponse == Roles(R.string.mentor, R.drawable.mentor)) {
                    mentorPositionResponse != null
                } else {
                    _positionResponse.value != null
                }
            }

            Questions.YEARS -> if (roleResponse == Roles(R.string.mentor, R.drawable.mentor)) {
                mentorPositionResponse != null
            } else {
                _yearsResponse.value != null
            }
        }
    }

    private fun createQuestionScreenData(): QuestionScreenData {
        return QuestionScreenData(
            questionIndex = questionIndex,
            questionCount = questionOrder.size,
            shouldShowPreviousButton = questionIndex > 0,
            shouldShowDoneButton = questionIndex == questionOrder.size - 1 || _roleResponse.value == Roles(
                R.string.mentee,
                R.drawable.mentee
            ) && questionIndex == 2,
            questions = questionOrder[questionIndex],
        )
    }
}

class QuestionsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionsViewModel::class.java)) {
            return QuestionsViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

enum class Questions {
    ROLE,
    PERSONALITY,
    POSITION,
    YEARS,
}

data class QuestionScreenData(
    val questionIndex: Int,
    val questionCount: Int,
    val shouldShowPreviousButton: Boolean,
    val shouldShowDoneButton: Boolean,
    val questions: Questions,
)