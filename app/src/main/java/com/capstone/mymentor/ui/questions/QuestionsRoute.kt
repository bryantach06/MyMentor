package com.capstone.mymentor.ui.questions

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.with
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.mymentor.R
import com.capstone.mymentor.models.Personality
import com.capstone.mymentor.models.Roles

private const val CONTENT_ANIMATION_DURATION = 300

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun QuestionsRoute(
    context: Context,
    onNavUp: () -> Unit,
) {
    val viewModel: QuestionsViewModel = viewModel(
        factory = QuestionsViewModelFactory()
    )

    val questionsScreenData = viewModel.questionScreenData

    BackHandler {
        if (!viewModel.onBackPressed()) {
            onNavUp
        }
    }

    QuestionsScreen(
        questionsScreenData = questionsScreenData,
        isNextEnabled = viewModel.isNextEnabled,
        onPreviousPressed = { viewModel.onPreviousPressed() },
        onNextPressed = { viewModel.onNextPressed() },
        onDonePressed = { viewModel.onDonePressed(context) }
    ) { paddingValues ->

        val modifier = Modifier.padding(paddingValues)

        AnimatedContent(
            targetState = questionsScreenData,
            transitionSpec = {
                val animationSpec: TweenSpec<IntOffset> =
                    tween(CONTENT_ANIMATION_DURATION)
                val direction = getTransitionDirection(
                    initialIndex = initialState.questionIndex,
                    targetIndex = targetState.questionIndex,
                )
                slideIntoContainer(
                    towards = direction,
                    animationSpec = animationSpec,
                ) with slideOutOfContainer(
                    towards = direction,
                    animationSpec = animationSpec
                )
            }
        ) { targetState ->
            val selectedRole = viewModel.roleResponse
            val selectedPersonality = viewModel.personalityResponse
            val isMenteeSelected = selectedRole == Roles(R.string.mentee, R.drawable.mentee)
            val isMentorSelected = selectedRole == Roles(R.string.mentor, R.drawable.mentor)
            val isHackerSelected =
                selectedPersonality == Personality(R.string.hacker, R.drawable.hacker)
            val isHustlerSelected =
                selectedPersonality == Personality(R.string.hustler, R.drawable.hustler)
            val isHipsterSelected =
                selectedPersonality == Personality(R.string.hipster, R.drawable.hipster)

            when (targetState.questions) {
                Questions.ROLE -> RoleQuestion(
                    selectedAnswer = viewModel.roleResponse,
                    onOptionsSelected = viewModel::onRoleResponse,
                    modifier = modifier
                )

                Questions.PERSONALITY -> PersonalityQuestion(
                    selectedAnswer = viewModel.personalityResponse,
                    onOptionsSelected = viewModel::onPersonalityResponse,
                    modifier = modifier
                )

                Questions.POSITION -> {

                    if (isMenteeSelected && isHackerSelected) {
                        MenteeHackerPositionQuestion(
                            selectedAnswer = viewModel.positionResponse,
                            onOptionsSelected = viewModel::onPositionResponse,
                        )
                    } else if (isHustlerSelected && isMenteeSelected) {
                        MenteeHustlerPositionQuestion(
                            selectedAnswer = viewModel.positionResponse,
                            onOptionsSelected = viewModel::onPositionResponse,
                        )
                    } else if (isHipsterSelected && isMenteeSelected) {
                        MenteeHipsterPositionQuestion(
                            selectedAnswer = viewModel.positionResponse,
                            onOptionsSelected = viewModel::onPositionResponse,
                        )
                    } else if (isMentorSelected && isHackerSelected) {
                        MentorHackerPositionQuestion(
                            selectedAnswer = viewModel.mentorPositionResponse,
                            onOptionsSelected = viewModel::onMentorPositionResponse,
                        )
                    } else if (isMentorSelected && isHustlerSelected) {
                        MentorHustlerPositionQuestion(
                            selectedAnswer = viewModel.mentorPositionResponse,
                            onOptionsSelected = viewModel::onMentorPositionResponse,
                        )
                    } else if (isMentorSelected && isHipsterSelected) {
                        MentorHipsterPositionQuestion(
                            selectedAnswer = viewModel.mentorPositionResponse,
                            onOptionsSelected = viewModel::onMentorPositionResponse,
                        )
                    }
                }

                Questions.YEARS -> {
                    if (isMentorSelected) {
                        MentorYearsPositionQuestion(
                            selectedAnswer = viewModel.yearsResponse,
                            onOptionsSelected = viewModel::onYearsResponse,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun getTransitionDirection(
    initialIndex: Int,
    targetIndex: Int
): AnimatedContentScope.SlideDirection {
    return if (targetIndex > initialIndex) {
        // Going forwards in the survey: Set the initial offset to start
        // at the size of the content so it slides in from right to left, and
        // slides out from the left of the screen to -fullWidth
        AnimatedContentScope.SlideDirection.Left
    } else {
        // Going back to the previous question in the set, we do the same
        // transition as above, but with different offsets - the inverse of
        // above, negative fullWidth to enter, and fullWidth to exit.
        AnimatedContentScope.SlideDirection.Right
    }
}