package com.capstone.mymentor.ui.questions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.capstone.mymentor.R
import com.capstone.mymentor.models.MenteePosition
import com.capstone.mymentor.models.MentorPosition
import com.capstone.mymentor.models.Roles
import com.capstone.mymentor.models.Personality
import com.capstone.mymentor.models.SingleChoicePersonalityQuestion
import com.capstone.mymentor.models.SingleChoiceMenteePositionQuestion
import com.capstone.mymentor.models.SingleChoiceMentorPositionQuestion
import com.capstone.mymentor.models.SingleChoiceMentorYearsQuestion
import com.capstone.mymentor.models.SingleChoiceRoleQuestion
import com.capstone.mymentor.models.YearsPosition

@Composable
fun PersonalityQuestion(
    selectedAnswer: Personality?,
    onOptionsSelected: (Personality) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoicePersonalityQuestion(
        titleResourceId = R.string.pick_personality,
        directionsResourceId = R.string.select_one,
        possibleAnswers = listOf(
            Personality(R.string.hacker, R.drawable.hacker),
            Personality(R.string.hustler, R.drawable.hustler),
            Personality(R.string.hipster, R.drawable.hipster),
        ),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionsSelected,
        modifier = modifier
    )
}

@Composable
fun RoleQuestion(
    selectedAnswer: Roles?,
    onOptionsSelected: (Roles) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceRoleQuestion(
        titleResourceId = R.string.pick_role,
        directionsResourceId = R.string.select_one,
        possibleAnswers = listOf(
            Roles(R.string.mentee, R.drawable.mentee),
            Roles(R.string.mentor, R.drawable.mentor)
        ),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionsSelected,
        modifier = modifier,
    )
}

@Composable
fun MenteeHackerPositionQuestion(
    selectedAnswer: MenteePosition?,
    onOptionsSelected: (MenteePosition) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceMenteePositionQuestion(
        titleResourceId = R.string.pick_position,
        directionsResourceId = R.string.select_one,
        possibleAnswers = listOf(
            MenteePosition(R.string.data_analyst),
            MenteePosition(R.string.data_engineer),
            MenteePosition(R.string.data_scientist),
            MenteePosition(R.string.front_end_dev),
            MenteePosition(R.string.back_end_dev),
            MenteePosition(R.string.full_stack_dev),
            MenteePosition(R.string.web_dev),
            MenteePosition(R.string.software_engineer),
            MenteePosition(R.string.information_security),
            MenteePosition(R.string.ai_specialist),
            MenteePosition(R.string.comp_network_specialist),
            MenteePosition(R.string.database_admin),
        ),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionsSelected,
        modifier = modifier,
    )
}

@Composable
fun MenteeHustlerPositionQuestion(
    selectedAnswer: MenteePosition?,
    onOptionsSelected: (MenteePosition) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceMenteePositionQuestion(
        titleResourceId = R.string.pick_position,
        directionsResourceId = R.string.select_one,
        possibleAnswers = listOf(
            MenteePosition(R.string.business_development),
            MenteePosition(R.string.technopreneur),
            MenteePosition(R.string.business_consultant),
            MenteePosition(R.string.risk_manager),
            MenteePosition(R.string.product_manager),
            MenteePosition(R.string.market_researcher),
            MenteePosition(R.string.financial_analyst),
            MenteePosition(R.string.digital_marketing),
            MenteePosition(R.string.entrepreneur),
            MenteePosition(R.string.digital_business_analyst),
            MenteePosition(R.string.operational_manager),
            MenteePosition(R.string.retail_manager),
            MenteePosition(R.string.business_executive),
        ),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionsSelected,
        modifier = modifier,
    )
}

@Composable
fun MenteeHipsterPositionQuestion(
    selectedAnswer: MenteePosition?,
    onOptionsSelected: (MenteePosition) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceMenteePositionQuestion(
        titleResourceId = R.string.pick_position,
        directionsResourceId = R.string.select_one,
        possibleAnswers = listOf(
            MenteePosition(R.string.graphics_designer),
            MenteePosition(R.string.uiux_designer),
            MenteePosition(R.string.business_consultant),
            MenteePosition(R.string.product_developer),
            MenteePosition(R.string.art_director),
            MenteePosition(R.string.web_designer),
            MenteePosition(R.string.art_entrepreneur),
        ),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionsSelected,
        modifier = modifier,
    )
}

@Composable
fun MentorHackerPositionQuestion(
    selectedAnswer: MentorPosition?,
    onOptionsSelected: (MentorPosition) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceMentorPositionQuestion(
        titleResourceId = R.string.pick_position,
        directionsResourceId = R.string.select_one,
        possibleAnswers = listOf(
            MentorPosition(R.string.data_analyst),
            MentorPosition(R.string.data_engineer),
            MentorPosition(R.string.data_scientist),
            MentorPosition(R.string.front_end_dev),
            MentorPosition(R.string.back_end_dev),
            MentorPosition(R.string.full_stack_dev),
            MentorPosition(R.string.web_dev),
            MentorPosition(R.string.software_engineer),
            MentorPosition(R.string.information_security),
            MentorPosition(R.string.ai_specialist),
            MentorPosition(R.string.comp_network_specialist),
            MentorPosition(R.string.database_admin),
        ),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionsSelected,
        modifier = modifier,
    )
}

@Composable
fun MentorHustlerPositionQuestion(
    selectedAnswer: MentorPosition?,
    onOptionsSelected: (MentorPosition) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceMentorPositionQuestion(
        titleResourceId = R.string.pick_position,
        directionsResourceId = R.string.select_one,
        possibleAnswers = listOf(
            MentorPosition(R.string.business_development),
            MentorPosition(R.string.technopreneur),
            MentorPosition(R.string.business_consultant),
            MentorPosition(R.string.risk_manager),
            MentorPosition(R.string.product_manager),
            MentorPosition(R.string.market_researcher),
            MentorPosition(R.string.financial_analyst),
            MentorPosition(R.string.digital_marketing),
            MentorPosition(R.string.entrepreneur),
            MentorPosition(R.string.digital_business_analyst),
            MentorPosition(R.string.operational_manager),
            MentorPosition(R.string.retail_manager),
            MentorPosition(R.string.business_executive),
        ),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionsSelected,
        modifier = modifier,
    )
}

@Composable
fun MentorHipsterPositionQuestion(
    selectedAnswer: MentorPosition?,
    onOptionsSelected: (MentorPosition) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceMentorPositionQuestion(
        titleResourceId = R.string.pick_position,
        directionsResourceId = R.string.select_one,
        possibleAnswers = listOf(
            MentorPosition(R.string.graphics_designer),
            MentorPosition(R.string.uiux_designer),
            MentorPosition(R.string.business_consultant),
            MentorPosition(R.string.product_developer),
            MentorPosition(R.string.art_director),
            MentorPosition(R.string.web_designer),
            MentorPosition(R.string.art_entrepreneur),
        ),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionsSelected,
        modifier = modifier,
    )
}

@Composable
fun MentorYearsPositionQuestion(
    selectedAnswer: YearsPosition?,
    onOptionsSelected: (YearsPosition) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceMentorYearsQuestion(
        titleResourceId = R.string.input_experience,
        directionsResourceId = R.string.select_one,
        possibleAnswers = listOf(
            YearsPosition(R.string.zero_to_five),
            YearsPosition(R.string.five_to_ten),
            YearsPosition(R.string.ten_to_fifteen),
            YearsPosition(R.string.more_than_fifteen),
        ),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionsSelected,
        modifier = modifier,
    )
}
