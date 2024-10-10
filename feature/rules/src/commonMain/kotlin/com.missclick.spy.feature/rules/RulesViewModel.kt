package com.missclick.spy.feature.rules

import androidx.lifecycle.ViewModel
import com.missclick.spy.resources.Res
import com.missclick.spy.resources.fifth_page_text
import com.missclick.spy.resources.fifth_page_title
import com.missclick.spy.resources.first_page_text
import com.missclick.spy.resources.first_page_title
import com.missclick.spy.resources.fourth_page_text
import com.missclick.spy.resources.fourth_page_title
import com.missclick.spy.resources.ic_hand
import com.missclick.spy.resources.ic_hat
import com.missclick.spy.resources.ic_hat_location
import com.missclick.spy.resources.ic_location
import com.missclick.spy.resources.ic_medal
import com.missclick.spy.resources.ic_reply
import com.missclick.spy.resources.ic_timer
import com.missclick.spy.resources.second_page_text
import com.missclick.spy.resources.second_page_title
import com.missclick.spy.resources.seventh_page_text
import com.missclick.spy.resources.seventh_page_title
import com.missclick.spy.resources.sixth_page_text
import com.missclick.spy.resources.sixth_page_title
import com.missclick.spy.resources.third_page_text
import com.missclick.spy.resources.third_page_title
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

class RulesViewModel() : ViewModel() {

    private val _viewState = MutableStateFlow(RulesViewState())
    val viewState: StateFlow<RulesViewState> = _viewState.asStateFlow()

    init {
        _viewState.update {
            it.copy(
                rules = getRules()
            )
        }
    }

    private fun getRules() : List<Rule> = listOf(
        Rule(
            icon = Res.drawable.ic_hat,
            title = Res.string.first_page_title,
            text = Res.string.first_page_text
        ),
        Rule(
            icon = Res.drawable.ic_hat_location,
            title = Res.string.second_page_title,
            text = Res.string.second_page_text
        ),
        Rule(
            icon = Res.drawable.ic_location,
            title = Res.string.third_page_title,
            text = Res.string.third_page_text
        ),
        Rule(
            icon = Res.drawable.ic_reply,
            title = Res.string.fourth_page_title,
            text = Res.string.fourth_page_text
        ),
        Rule(
            icon = Res.drawable.ic_hand,
            title = Res.string.fifth_page_title,
            text = Res.string.fifth_page_text
        ),
        Rule(
            icon = Res.drawable.ic_medal,
            title = Res.string.sixth_page_title,
            text = Res.string.sixth_page_text
        ),
        Rule(
            icon = Res.drawable.ic_timer,
            title = Res.string.seventh_page_title,
            text = Res.string.seventh_page_text
        ),
    )

}

data class RulesViewState(
    val rules: List<Rule> = emptyList(),
)

data class Rule(
    val icon: DrawableResource,
    val title: StringResource,
    val text: StringResource,
)