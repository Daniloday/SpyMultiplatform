package com.missclick.spy.feature.rules

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.missclick.spy.core.ui.kit.Image
import com.missclick.spy.core.ui.kit.Tab
import com.missclick.spy.core.ui.kit.buttons.PrimaryButton
import com.missclick.spy.core.ui.theme.AppTheme
import com.missclick.spy.resources.Res
import com.missclick.spy.resources.next
import com.missclick.spy.resources.skip
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
internal fun RulesRoute(
    modifier: Modifier = Modifier,
    onSkipClick: () -> Unit,
    vm: RulesViewModel = koinViewModel(),
) {

    val viewState by vm.viewState.collectAsState()

    RulesScreen(
        modifier = modifier,
        onSkipClick = onSkipClick,
        viewState = viewState,
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RulesScreen(
    modifier: Modifier = Modifier,
    onSkipClick: () -> Unit,
    viewState: RulesViewState,
) {

    val horizontalPagerState = rememberPagerState {
        viewState.rules.size
    }

    Column(modifier = modifier) {
        ViewPager(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            state = horizontalPagerState,
            rules = viewState.rules
        )
        Tabs(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp),
            count = horizontalPagerState.pageCount,
            selectedIndex = horizontalPagerState.currentPage
        )
        PrimaryButton(
            modifier = Modifier.padding(bottom = 16.dp),
            onClick = onSkipClick,
            text = if (horizontalPagerState.canScrollForward)
                stringResource(resource = Res.string.skip)
            else
                stringResource(resource = Res.string.next)
        )
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ViewPager(
    modifier: Modifier = Modifier,
    state: PagerState,
    rules: List<Rule>,
) {
    HorizontalPager(
        modifier = modifier,
        state = state
    ) { currentPage ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 8.dp)
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                Image(painter = painterResource(resource = rules[currentPage].icon))
                Text(
                    text = stringResource(resource = rules[currentPage].title),
                    style = AppTheme.types.h42,
                    color = AppTheme.colors.primary,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = stringResource(resource = rules[currentPage].text),
                    style = AppTheme.types.h22,
                    color = AppTheme.colors.primary,
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}

@Composable
private fun Tabs(
    modifier: Modifier = Modifier,
    count: Int,
    selectedIndex: Int,
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(count) { currentIndex ->
                Tab(isSelected = currentIndex == selectedIndex)
            }
        }
    }
}

