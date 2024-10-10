package com.missclick.spy.feature.guide

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.missclick.spy.core.ui.kit.AppDivider
import com.missclick.spy.core.ui.kit.buttons.PrimaryButton
import com.missclick.spy.core.ui.theme.AppTheme
import com.missclick.spy.resources.Res
import com.missclick.spy.resources.guide_fifth_text
import com.missclick.spy.resources.guide_fifth_title
import com.missclick.spy.resources.guide_first_text
import com.missclick.spy.resources.guide_first_title
import com.missclick.spy.resources.guide_fourth_text
import com.missclick.spy.resources.guide_fourth_title
import com.missclick.spy.resources.guide_second_text
import com.missclick.spy.resources.guide_second_title
import com.missclick.spy.resources.guide_sixth_text
import com.missclick.spy.resources.guide_sixth_title
import com.missclick.spy.resources.guide_third_text
import com.missclick.spy.resources.guide_third_title
import com.missclick.spy.resources.ic_back
import com.missclick.spy.resources.ic_qa
import com.missclick.spy.resources.play
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun GuideRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    GuideScreen(
        modifier = modifier,
        onBackClick = onBackClick
    )
}

@Composable
private fun GuideScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        TopBar(onBackClick = onBackClick)
        Guide(onClickPlay = onBackClick)
    }
}

@Composable
private fun Guide(
    modifier: Modifier = Modifier,
    onClickPlay: () -> Unit,
) {
    val scrollState = rememberScrollState()

    val guide = listOf(
        Res.string.guide_first_title to Res.string.guide_first_text,
        Res.string.guide_second_title to Res.string.guide_second_text,
        Res.string.guide_third_title to Res.string.guide_third_text,
        Res.string.guide_fourth_title to Res.string.guide_fourth_text,
        Res.string.guide_fifth_title to Res.string.guide_fifth_text,
        Res.string.guide_sixth_title to Res.string.guide_sixth_text,
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(208.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(resource = Res.drawable.ic_qa),
            contentDescription = null,
            tint = AppTheme.colors.primary
        )
        guide.forEach {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(resource = it.first),
                    color = AppTheme.colors.secondary,
                    style = AppTheme.types.h28
                )
                Text(
                    text = stringResource(resource = it.second),
                    color = AppTheme.colors.primary,
                    style = AppTheme.types.h22
                )
            }
        }

        PrimaryButton(
            onClick = onClickPlay,
            text = stringResource(resource = Res.string.play)
        )


    }
}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(AppTheme.dimens.topBarHeight),
        contentAlignment = Alignment.CenterStart
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(resource = Res.drawable.ic_back),
                tint = AppTheme.colors.primary,
                contentDescription = null
            )
        }
        AppDivider(modifier = Modifier.align(Alignment.BottomCenter))
    }
}