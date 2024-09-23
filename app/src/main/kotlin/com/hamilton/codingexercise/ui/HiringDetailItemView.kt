package com.hamilton.codingexercise.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hamilton.codingexercise.R
import com.hamilton.codingexercise.ui.models.HiringDetailUiModel
import com.hamilton.codingexercise.ui.theme.CodingExerciseTheme
import com.hamilton.codingexercise.ui.utils.PreviewLightAndDark

/**
 * Composable function to display a hiring detail item in a card format.
 *
 * This function displays information about a `HiringDetailUiModel`, showing its `listId` and `name`
 * in a structured format.
 *
 * @param modifier Modifier to be applied to the card. The default value is `Modifier`, allowing
 * further customization from the calling site.
 * @param hiringDetailUiModel A `HiringDetailUiModel` object that holds the data to be displayed,
 * such as `listId` and `name`.
 */
@Composable
fun HiringDetailItemView(modifier: Modifier = Modifier, hiringDetailUiModel: HiringDetailUiModel) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                    text = stringResource(R.string.data_item_view_list_id),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier.padding(bottom = 16.dp, start = 16.dp),
                    text = hiringDetailUiModel.listId.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    modifier = Modifier.padding(top = 16.dp, end = 16.dp),
                    text = stringResource(R.string.data_item_view_item),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier.padding(bottom = 16.dp, end = 16.dp),
                    text = hiringDetailUiModel.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@PreviewLightAndDark
@Composable
private fun PreviewDataItemView() {
    val hiringDetailUiModel = HiringDetailUiModel(
        listId = 5,
        name = "Item 684"
    )
    CodingExerciseTheme {
        HiringDetailItemView(hiringDetailUiModel = hiringDetailUiModel)
    }
}
