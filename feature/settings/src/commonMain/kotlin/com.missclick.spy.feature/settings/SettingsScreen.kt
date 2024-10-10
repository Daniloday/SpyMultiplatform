package com.missclick.spy.feature.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.missclick.spy.core.ui.kit.AppDivider
import com.missclick.spy.core.ui.theme.AppTheme
import com.missclick.spy.resources.Res
import com.missclick.spy.resources.ic_back
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun SettingsRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    vm: SettingsViewModel = koinViewModel(),
) {

    val viewState by vm.viewState.collectAsState()

    SettingsScreen(
        modifier = modifier,
        onBackClick = onBackClick,
        viewState = viewState,
        onLanguageClick = vm::selectLanguage
    )
}

@Composable
private fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewState: SettingsViewState,
    onBackClick: () -> Unit,
    onLanguageClick: (LanguageView) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        TopBar(onBackClick = onBackClick)
        Languages(
            languages = viewState.languages,
            onLanguageClick = onLanguageClick
        )
    }
}

@Composable
private fun Languages(
    modifier: Modifier = Modifier,
    languages: List<LanguageView>,
    onLanguageClick: (LanguageView) -> Unit,
){
    LazyColumn(
        modifier = modifier
    ) {
        items(languages) { language ->
            LanguageCard(
                onLanguageClick = {
                    onLanguageClick(language)
                },
                languageName = language.name,
                isSelected = language.isSelected
            )
        }
    }
}

@Composable
private fun LanguageCard(
    modifier: Modifier = Modifier,
    languageName: String,
    isSelected: Boolean,
    onLanguageClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = AppTheme.shapes.rectangle,
        onClick = onLanguageClick,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier
                    .padding(start = 24.dp)
                    .align(Alignment.CenterStart),
                text = languageName,
                style = AppTheme.types.h28,
                color = if (isSelected) AppTheme.colors.secondary else AppTheme.colors.primary
            )
            AppDivider(modifier = Modifier.align(Alignment.BottomCenter))
        }
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