package com.ukhyun.layout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.*
import com.ukhyun.layout.padding
import com.ukhyun.layout.ui.theme.LayoutTheme

class Constraint : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
//                    PhotographerCard()
                    TwoText(
                        text1 = "HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1HELLO1",
                        text2 = "HELLO2"
                    )
                }
            }
        }
    }
}

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        val (button1, button2, text) = createRefs()

        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button 1")
        }

        Text("Text", Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 16.dp)
            centerAround(button1.end)
        })

        val barrier = createEndBarrier(button1, text)
        Button(onClick = { /*TODO*/ }, modifier = Modifier.constrainAs(button2) {
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(barrier )
        }) {
            Text(text = "Button 2")
        }
    }
}

@Composable
fun TwoText(modifier: Modifier = Modifier, text1: String, text2: String) {
    Row(modifier = modifier.height(IntrinsicSize.Min)) {
        Text(
            text = text1, modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(
                    Alignment.Start
                )
        )

        Divider(
            color = Color.Black, modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        Text(
            text = text2, modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .wrapContentWidth(
                    Alignment.End
                )
        )
    }
}


@Composable
fun LargeConstraintLayout() {
    ConstraintLayout() {
        val text = createRef()

        val guideline = createGuidelineFromStart(0.5f)
        Text("This is a very very very very very very very long text", Modifier.constrainAs(text) {
            linkTo(start = guideline, end = parent.end)
            width = Dimension.preferredWrapContent
        })
    }
}

@Composable
fun DecoupledConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(margin = 16.dp) // Portrait constraints
        } else {
            decoupledConstraints(margin = 32.dp) // Landscape constraints
        }

        ConstraintLayout(constraints) {
            Button(
                onClick = { /* Do something */ },
                modifier = Modifier.layoutId("button")
            ) {
                Text("Button")
            }

            Text("Text", Modifier.layoutId("text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            top.linkTo(parent.top, margin = margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConstraintLayoutContentPreview() {
    LayoutTheme {
        ConstraintLayoutContent()
    }
}

@Preview(showBackground = true)
@Composable
fun Preview2() {
    LayoutTheme {
        LargeConstraintLayout()
    }
}

@Preview(showBackground = true)
@Composable
fun Preview3() {
    LayoutTheme {
        DecoupledConstraintLayout()
    }
}
