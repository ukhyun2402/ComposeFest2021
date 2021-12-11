package com.ukhyun.layout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import coil.compose.rememberImagePainter
import com.ukhyun.layout.ui.theme.LayoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
//                    PhotographerCard()
                    BodyContent()
                }
            }
        }
    }
}

val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)

@Composable
fun ImageList(lazyListState: LazyListState) {

    LazyColumn(state = lazyListState) {
        items(100) {
            ImageListItem(index = it)
        }
    }
}

@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberImagePainter(
                data = "https://www.clipartmax.com/png/full/405-4050774_avatar-icon-flat-icon-shop-download-free-icons-for-avatar-icon-flat.png"
            ),
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text("Item $index", style = MaterialTheme.typography.subtitle1)
    }
}

fun Modifier.firstBaselineToTop(firstBaselineToTop: Dp) =
    this.then(layout { measurable, constraints ->

        val placeable = measurable.measure(constraints)
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY
        layout(placeable.width, height) {
            placeable.placeRelative(0, placeableY)
        }
    }
    )

@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(content = content, modifier = modifier) { measurable, constraints ->

        val placeable = measurable.map { measurable -> measurable.measure(constraints) }
        var yPosition = 0
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeable.forEach {
                it.placeRelative(x = 0, y = yPosition)
                yPosition += it.height
            }
        }
    }
}

@Composable
fun CustomColumn() {
    MyOwnColumn() {
        Text("가나다")
        Text("가나다")
        Text("가나다")
    }
}

//@Preview
//@Composable
//fun PreviewCustomColumn() {
//    LayoutTheme {
//        CustomColumn()
//    }
//}

@Composable
fun SimpleList() {

    var scrollState = rememberScrollState()

    Column(
        Modifier
            .verticalScroll(scrollState)
            .fillMaxWidth()
    ) {
        repeat(100) {
            Text("Item #$it")
        }
    }
}

@Composable
fun LazyList() {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(100) {
            Text("Item $it")
        }
    }
}

@Composable
fun LayoutCodelab() {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "LayoutCodelab") }, actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Favorite, contentDescription = null)
                }
            }
        )
    }) { innerpadding ->
        BodyContent(Modifier.padding(8.dp))
    }

}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
//    val listSize = 100
//    val scrollState = rememberLazyListState()
//    val coroutineScope = rememberCoroutineScope()

//    Column(modifier = modifier) {
//        Text(text = "Hit there!")
//        Text(text = "Thank for going through the Layouts codelab")
//        Row {
//            Button(onClick = {
//                coroutineScope.launch {
//                    // 0 is the first item index
//                    scrollState.animateScrollToItem(0)
//                }
//            }) {
//                Text("Scroll to the top")
//            }
//
//            Button(onClick = {
//                coroutineScope.launch {
//                    // listSize - 1 is the last index of the list
//                    scrollState.animateScrollToItem(listSize - 1)
//                }
//            }) {
//                Text("Scroll to the end")
//            }
//        }
////        SimpleList()
////        LazyList()
//        ImageList(scrollState)
//    }
    Row(modifier = modifier
        .background(color = Color.LightGray)
        .padding(8.dp)
        .horizontalScroll(rememberScrollState())) {

        StaggeredGrid(modifier = modifier, rows = 3) {
            for (topic in topics) {
                Chip(modifier = modifier.padding(8.dp), text = topic)
            }
        }
    }
}


@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {
    Row(
        modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable { }
            .padding(16.dp)
            .fillMaxWidth()) {
        Surface(
            modifier = Modifier.size(50.dp), shape = CircleShape, color = MaterialTheme
                .colors.onSurface.copy(alpha = 0.2f)
        ) {
        }
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(alignment = Alignment.CenterVertically)
        ) {
            Text("Alfred Sisley", fontWeight = FontWeight.Bold)
            // 투명도를 위한 설정
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("3 minutes ago", style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        // Keep track of the width of each row
        val rowWidths = IntArray(rows) { 0 }

        // Keep track of the max height of each row
        val rowHeights = IntArray(rows) { 0 }

        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.mapIndexed { index, measurable ->
            // Measure each child
            val placeable = measurable.measure(constraints)

            // Track the width and max height of each row
            val row = index % rows
            rowWidths[row] += placeable.width
            rowHeights[row] = Math.max(rowHeights[row], placeable.height)

            placeable
        }

        // Grid's width is the widest row
        val width = rowWidths.maxOrNull()
            ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth)) ?: constraints.minWidth

        // Grid's height is the sum of the tallest element of each row
        // coerced to the height constraints
        val height = rowHeights.sumOf { it }
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        // Y of each row, based on the height accumulation of previous rows
        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
        }

        // Set the size of the parent layout
        layout(width, height) {
            // x co-ord we have placed up to, per row
            val rowX = IntArray(rows) { 0 }

            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(
                    x = rowX[row],
                    y = rowY[row]
                )
                rowX[row] += placeable.width
            }
        }
    }
}

@Composable
fun Chip(modifier: Modifier = Modifier, text: String) {
    Card(
        modifier = modifier.clickable {  },
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(Modifier.width(4.dp))
            Text(text = text)
        }
    }
}

@Stable
fun Modifier.padding(all: Dp) =
    this.then(PaddingModifier(start = all, top = all, end = all, bottom = all, rtlAware = true))

private class PaddingModifier(
    val start: Dp = 0.dp,
    val top: Dp = 0.dp,
    val end: Dp = 0.dp,
    val bottom: Dp = 0.dp,
    val rtlAware: Boolean,
) : LayoutModifier {

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val horizontal = start.roundToPx() + end.roundToPx()
        val vertical = top.roundToPx() + bottom.roundToPx()

        val placeable = measurable.measure(constraints.offset(-horizontal, -vertical))
        val width = constraints.constrainWidth(placeable.width + horizontal)
        val height = constraints.constrainHeight(placeable.height + vertical)
        return layout(width, height) {
            if (rtlAware) {
                placeable.placeRelative(start.roundToPx(), top.roundToPx())
            } else {
                placeable.place(start.roundToPx(), top.roundToPx())
            }
        }
    }

}
//
//@Preview
//@Composable
//fun ChipPreview() {
//    LayoutTheme() {
//        Chip(text = "GOOD")
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    LayoutTheme {
//        PhotographerCard()
//    }
//}

@Preview
@Composable
fun ScaffoldPreview() {
    LayoutTheme {
        LayoutCodelab()
    }
}

@Preview
@Composable
fun LayoutsCodelabPreview() {
    LayoutTheme {
        BodyContent()
    }
}