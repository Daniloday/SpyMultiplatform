package com.missclick.spy.feature.words

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.missclick.spy.core.common.Constant.MIN_LOCATIONS_TO_PLAY
import com.missclick.spy.core.ui.kit.AppDivider
import com.missclick.spy.core.ui.kit.AppTextField
import com.missclick.spy.core.ui.kit.buttons.PrimaryButton
import com.missclick.spy.core.ui.kit.buttons.SecondaryButton
import com.missclick.spy.core.ui.theme.AppTheme
import com.missclick.spy.resources.Res
import com.missclick.spy.resources.add_word
import com.missclick.spy.resources.choose
import com.missclick.spy.resources.ic_back
import com.missclick.spy.resources.ic_garbage
import com.missclick.spy.resources.ic_ok
import com.missclick.spy.resources.print_word
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

private const val LOCATION_NAME_MAX_LENGTH = 20

@Composable
internal fun WordsRoute(
    modifier: Modifier = Modifier,
    selectedCollectionName: String,
    onBackClick: () -> Unit,
    onSelectCollection: () -> Unit,
    vm: WordsViewModel = koinViewModel(),
) {

    val viewState by vm.viewState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        vm.loadData(selectedCollectionName)
    }

    val coroutineScope = rememberCoroutineScope()

    WordsScreen(
        modifier = modifier,
        onBackClick = onBackClick,
        onSelectCollection = {
            coroutineScope.launch {
                vm.saveCollection()
                onSelectCollection()
            }
        },
        onDeleteCollectionClick = {
            vm.deleteCollection()
            onBackClick()
        },
        onDeleteWordClick = vm::deleteWord,
        onAddWordClick = vm::addWord,
        onNewWordChange = vm::onNewWordChange,
        onSaveNewWordClick = vm::saveNewWord,
        viewState = viewState,
    )

}

@Composable
fun WordsScreen(
    modifier: Modifier = Modifier,
    viewState: WordsViewState,
    onBackClick: () -> Unit,
    onDeleteCollectionClick: () -> Unit,
    onDeleteWordClick: (String) -> Unit,
    onAddWordClick: () -> Unit,
    onSelectCollection: () -> Unit,
    onSaveNewWordClick: () -> Unit,
    onNewWordChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        when (viewState) {
            is WordsViewState.Loading -> Unit
            is WordsViewState.Success -> {
                TopBar(
                    onBackClick = onBackClick,
                    onDeleteClick = onDeleteCollectionClick,
                    collectionName = viewState.collectionName,
                    isDeletable = viewState.isEditable
                )
                WordsScreenSuccess(
                    modifier = modifier,
                    onSelectCollection = onSelectCollection,
                    onDeleteWordClick = onDeleteWordClick,
                    onAddWordClick = onAddWordClick,
                    onNewWordChange = onNewWordChange,
                    onSaveNewWordClick = onSaveNewWordClick,
                    viewState = viewState
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.WordsScreenSuccess(
    modifier: Modifier = Modifier,
    viewState: WordsViewState.Success,
    onSelectCollection: () -> Unit,
    onDeleteWordClick: (String) -> Unit,
    onAddWordClick: () -> Unit,
    onSaveNewWordClick: () -> Unit,
    onNewWordChange: (String) -> Unit,
) {

    val lazyListState = rememberLazyListState()

    LaunchedEffect(key1 = viewState.isEnteringNewWord) {
        if (viewState.isEnteringNewWord && viewState.words.isNotEmpty()) {
            lazyListState.animateScrollToItem(viewState.words.lastIndex)
        }
    }

    if (viewState.isEditable) {
        SecondaryButton(
            modifier = Modifier.padding(vertical = 16.dp),
            onClick = onAddWordClick,
            text = stringResource(resource = Res.string.add_word)
        )
    }

    LazyColumn(
        modifier = modifier.weight(1f),
        state = lazyListState
    ) {
        items(viewState.words) { word ->
            WordCard(
                modifier = Modifier,//.animateItem(),//todo
                name = word,
                isDeletable = viewState.isEditable,
                onDeleteClick = {
                    onDeleteWordClick(word)
                }
            )
        }
        if (viewState.isEnteringNewWord) {
            item {
                AddNewWordCard(
                    name = viewState.newWord,
                    onValueChange = onNewWordChange,
                    onSaveClick = onSaveNewWordClick
                )
            }
        }
    }
    PrimaryButton(
        modifier = Modifier.padding(vertical = 16.dp),
        enabled = viewState.words.size >= MIN_LOCATIONS_TO_PLAY,
        onClick = onSelectCollection,
        text = stringResource(resource = Res.string.choose)
    )
}

@Composable
private fun AddNewWordCard(
    modifier: Modifier = Modifier,
    name: String,
    onSaveClick: () -> Unit,
    onValueChange: (String) -> Unit,
) {

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = AppTheme.shapes.rectangle,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AppTextField(
                modifier = Modifier.focusRequester(focusRequester),
                value = name,
                maxLength = LOCATION_NAME_MAX_LENGTH,
                onDone = onSaveClick,
                onValueChange = onValueChange,
                placeholder = stringResource(resource = Res.string.print_word)
            )
            IconButton(
                onClick = onSaveClick,
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(resource = Res.drawable.ic_ok),
                    tint = AppTheme.colors.primary,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun WordCard(
    modifier: Modifier = Modifier,
    name: String,
    isDeletable: Boolean,
    onDeleteClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = AppTheme.shapes.rectangle,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(

                text = name,
                style = AppTheme.types.h28,
                color = AppTheme.colors.primary
            )
            if (isDeletable) {
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(resource = Res.drawable.ic_garbage),
                        tint = AppTheme.colors.primary,
                        contentDescription = null
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(32.dp))
            }
        }
    }
}


@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    isDeletable: Boolean,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
    collectionName: String,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(AppTheme.dimens.topBarHeight),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(resource = Res.drawable.ic_back),
                    tint = AppTheme.colors.primary,
                    contentDescription = null
                )
            }
            Text(
                text = collectionName,
                style = AppTheme.types.h28,
                color = AppTheme.colors.primary
            )
            if (isDeletable) {
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(resource = Res.drawable.ic_garbage),
                        tint = AppTheme.colors.primary,
                        contentDescription = null
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(32.dp))
            }

        }
        AppDivider(modifier = Modifier.align(Alignment.BottomCenter))
    }
}