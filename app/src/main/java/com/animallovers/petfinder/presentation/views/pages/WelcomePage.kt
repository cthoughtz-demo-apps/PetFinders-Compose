package com.animallovers.petfinder.presentation.views.pages

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.animallovers.petfinder.R
import com.animallovers.petfinder.presentation.navigation.Pages
import com.animallovers.petfinder.presentation.util.OnBoardingPage
import com.animallovers.petfinder.presentation.util.isThreeButtonNavSystem
import com.animallovers.petfinder.presentation.viewmodel.WelcomeViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WelcomePage(
    navController: NavHostController,
    welcomeViewModel: WelcomeViewModel = hiltViewModel()
) {

    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third
    )

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    val buttonText = remember {
        mutableStateOf("Next")
    }

    val bottomPaddingLayout = if (isThreeButtonNavSystem()) 60.dp else 20.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = bottomPaddingLayout)
    ) {

        HorizontalPager(
            modifier = Modifier.wrapContentSize(),
            count = 3,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { position ->
            PagerScreen(onBoardingPage = pages[position])
        }

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {

            val (indicator, bottomRow) = createRefs()

            AnimatedPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .padding(top = 20.dp, start = 40.dp)
                    .constrainAs(indicator) {
                        top.linkTo(parent.top)
                    }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .constrainAs(bottomRow) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = {
                    welcomeViewModel.saveOnBoardingState(completed = true)
                    navController.popBackStack()
                    navController.navigate(Pages.Home.route)
                }) {
                    Text(
                        "Skip",
                        modifier = Modifier
                            .padding(2.dp),
                        textAlign = TextAlign.Left,
                        color = Color.Black
                    )
                }

                Button(
                    onClick = {
                        if (buttonText.value == "Next") {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        } else {
                            welcomeViewModel.saveOnBoardingState(completed = true)
                            navController.popBackStack()
                            navController.navigate(Pages.Home.route)
                        }
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.purple)
                    )
                ) {
                    if (pagerState.currentPage == 2) {
                        buttonText.value = "Lets Get Started"
                    } else {
                        buttonText.value = "Next"
                    }
                    Text(buttonText.value)
                }
            }
        }
    }
}


@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {

    val DMSans = FontFamily(
        androidx.compose.ui.text.font.Font(R.font.dmsans, FontWeight.Normal),  // Regular
        androidx.compose.ui.text.font.Font(R.font.dmsans, FontWeight.Medium),  // Medium (500)
        androidx.compose.ui.text.font.Font(R.font.dmsans, FontWeight.Bold),      // Bold (700)
        androidx.compose.ui.text.font.Font(R.font.dmsans, FontWeight.ExtraBold), // Bold (800)
        androidx.compose.ui.text.font.Font(R.font.dmsans, FontWeight.Black),  // Bold (900)
        androidx.compose.ui.text.font.Font(
            R.font.dmsans,
            FontWeight.Normal,
            FontStyle.Italic
        ) // Italic
    )

    ConstraintLayout {
        val (onboardingImage, title, subtext) = createRefs()

        Image(
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = "Pager Image",
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(onboardingImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentScale = ContentScale.FillWidth
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .constrainAs(title) {
                    top.linkTo(onboardingImage.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = onBoardingPage.title,
            lineHeight = 30.sp,
            fontSize = 30.sp,
            fontFamily = DMSans,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Left,
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .constrainAs(subtext) {
                    top.linkTo(title.bottom)
                    start.linkTo(title.start)
                },
            text = onBoardingPage.description,
            color = colorResource(R.color.light_grey),
            fontSize = 16.sp,
            fontFamily = DMSans,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Left
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AnimatedPagerIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    dotSize: Dp = 4.dp,
    currentDotLength: Dp = 24.dp,
    dotSpacing: Dp = 8.dp,
    inactiveColor: Color = Color.LightGray.copy(alpha = 0.5f),
    activeColor: Color = colorResource(R.color.purple)
) {
    val transition = updateTransition(pagerState.currentPage, label = "pager_transition")

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dotSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pagerState.pageCount) { index ->
            val width by transition.animateDp(
                transitionSpec = { spring(dampingRatio = 0.6f) },
                label = "width_animation"
            ) { current ->
                if (index == current) currentDotLength else dotSize
            }

            val color by transition.animateColor(
                label = "color_animation"
            ) { current ->
                if (index == current) activeColor else inactiveColor
            }

            Box(
                modifier = Modifier
                    .size(width = width, height = dotSize)
                    .clip(RoundedCornerShape(50))
                    .background(color)
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun FirstOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPage = OnBoardingPage.First)
    }
}

@Composable
@Preview(showBackground = true)
fun SecondOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPage = OnBoardingPage.Second)
    }
}

@Composable
@Preview(showBackground = true)
fun ThirdOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPage = OnBoardingPage.Third)
    }
}


@Composable
@Preview(showBackground = true)
fun WelcomeScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        WelcomePage(rememberNavController())
    }
}