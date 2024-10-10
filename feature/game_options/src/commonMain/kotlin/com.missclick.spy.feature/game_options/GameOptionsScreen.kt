package com.missclick.spy.feature.game_options

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.missclick.spy.core.common.Constant.PLAYERS_MAX
import com.missclick.spy.core.common.Constant.PLAYERS_MIN
import com.missclick.spy.core.common.Constant.SPIES_MAX
import com.missclick.spy.core.common.Constant.SPIES_MIN
import com.missclick.spy.core.common.Constant.TIMER_MAX
import com.missclick.spy.core.common.Constant.TIMER_MIN
import com.missclick.spy.core.ui.kit.AppDivider
import com.missclick.spy.core.ui.kit.FrameText
import com.missclick.spy.core.ui.kit.buttons.TriangleButton
import com.missclick.spy.core.ui.kit.buttons.PrimaryButton
import com.missclick.spy.core.ui.theme.AppTheme
import com.missclick.spy.resources.Res
import com.missclick.spy.resources.ic_book
import com.missclick.spy.resources.ic_settings
import com.missclick.spy.resources.min
import com.missclick.spy.resources.players
import com.missclick.spy.resources.set
import com.missclick.spy.resources.spies
import com.missclick.spy.resources.start
import com.missclick.spy.resources.timer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun GameOptionsRoute(
    modifier: Modifier = Modifier,
    onSettingsClick: () -> Unit,
    onGuideClick: () -> Unit,
    onStartClick: () -> Unit,
    onSelectSetClick: () -> Unit,
    vm: GameOptionsViewModel = koinViewModel(),
) {

    val viewState by vm.viewState.collectAsState()

    GameOptionsScreen(
        modifier = modifier,
        onStart = onStartClick,
        onGuideClick = onGuideClick,
        onSettingsClick = onSettingsClick,
        vm = vm,
        viewState = viewState,
        onSelectSetClick = onSelectSetClick,
    )

}

@Composable
private fun GameOptionsScreen(
    modifier: Modifier = Modifier,
    onStart: () -> Unit,
    onSettingsClick: () -> Unit,
    onGuideClick: () -> Unit,
    onSelectSetClick: () -> Unit,
    viewState: GameOptionsViewState,
    vm: GameOptionsViewModel,
) {
    Column(
        modifier = modifier
            .padding(bottom = 16.dp)
            .fillMaxSize()
    ) {
        TopBar(
            onGuideClick = onGuideClick,
            onSettingsClick = onSettingsClick,
        )
        when (viewState){
            is GameOptionsViewState.Success -> GameOptionsSuccess(
                onStart = onStart,
                onSelectSetClick = onSelectSetClick,
                vm = vm,
                viewState = viewState
            )
            is GameOptionsViewState.Loading -> Unit
        }
    }
}

@Composable
private fun ColumnScope.GameOptionsSuccess(
    modifier: Modifier = Modifier,
    onStart: () -> Unit,
    onSelectSetClick: () -> Unit,
    viewState: GameOptionsViewState.Success,
    vm: GameOptionsViewModel,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .weight(1f),
        contentAlignment = Alignment.Center
    ) {
        Options(
            modifier = Modifier,
            vm = vm,
            viewState = viewState,
            onSelectSetClick = onSelectSetClick
        )
    }
    PrimaryButton(
        onClick = onStart,
        text = stringResource(Res.string.start)
    )
}

@Composable
private fun Options(
    modifier: Modifier = Modifier,
    viewState: GameOptionsViewState.Success,
    onSelectSetClick: () -> Unit,
    vm: GameOptionsViewModel,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Option(
            name = stringResource(resource = Res.string.players),
            value = viewState.playersCount.toString(),
            isDownEnabled = viewState.playersCount > PLAYERS_MIN,
            isUpEnabled = viewState.playersCount < PLAYERS_MAX,
            onUpClick = vm::onUpPlayers,
            onDownClick = vm::onDownPlayers
        )
        Option(
            name = stringResource(resource = Res.string.spies),
            value = viewState.spiesCount.toString(),
            isDownEnabled = viewState.spiesCount > SPIES_MIN,
            isUpEnabled = viewState.spiesCount < SPIES_MAX
                    && viewState.spiesCount < viewState.playersCount - 1,
            onUpClick = vm::onUpSpies,
            onDownClick = vm::onDownSpies
        )
        Option(
            name = stringResource(resource = Res.string.timer),
            value = "${viewState.time} ${stringResource(resource = Res.string.min)}",
            isDownEnabled = viewState.time > TIMER_MIN,
            isUpEnabled = viewState.time < TIMER_MAX,
            onUpClick = vm::onUpTime,
            onDownClick = vm::onDownTime
        )
        CollectionSelector(
            value = viewState.collectionName,
            onSelectClick = onSelectSetClick
        )
    }
}

@Composable
private fun CollectionSelector(
    modifier: Modifier = Modifier,
    value: String,
    onSelectClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(resource = Res.string.set),
            color = AppTheme.colors.primary,
            style = AppTheme.types.h28,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            FrameText(
//                modifier = Modifier.defaultMinSize(minWidth = 100.dp),
                text = value
            )
            TriangleButton(
                modifier = Modifier.size(48.dp),
                onClick = onSelectClick
            )
        }
    }
}

@Composable
private fun Option(
    modifier: Modifier = Modifier,
    name: String,
    value: String,
    isDownEnabled: Boolean,
    isUpEnabled: Boolean,
    onUpClick: () -> Unit,
    onDownClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            color = AppTheme.colors.primary,
            style = AppTheme.types.h28,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TriangleButton(
                modifier = Modifier
                    .size(48.dp)
                    .rotate(180f),
                isEnabled = isDownEnabled,
                onClick = onDownClick
            )
            FrameText(text = value)
            TriangleButton(
                modifier = Modifier.size(48.dp),
                isEnabled = isUpEnabled,
                onClick = onUpClick
            )
        }
    }
}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    onGuideClick: () -> Unit,
    onSettingsClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .height(AppTheme.dimens.topBarHeight)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onGuideClick) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(resource = Res.drawable.ic_book),
                    contentDescription = null,
                    tint = AppTheme.colors.primary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = onSettingsClick) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(resource = Res.drawable.ic_settings),
                    contentDescription = null,
                    tint = AppTheme.colors.primary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        AppDivider(modifier = Modifier.align(Alignment.BottomCenter))
    }
}