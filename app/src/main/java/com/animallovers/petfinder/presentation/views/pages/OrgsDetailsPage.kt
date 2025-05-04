package com.animallovers.petfinder.presentation.views.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.animallovers.petfinder.R
import com.animallovers.petfinder.domain.model.organization.GetOrganizationResponse
import com.animallovers.petfinder.presentation.util.Constants.PLACE_HOLDER
import com.animallovers.petfinder.presentation.util.PetFinderResult
import com.animallovers.petfinder.presentation.util.isThreeButtonNavSystem
import com.animallovers.petfinder.presentation.viewmodel.OrganizationViewModel
import com.animallovers.petfinder.presentation.viewmodel.TokenViewModel

private const val TAG = "OrgsDetailsPage"

@Composable
fun OrgsDetailsPage(
    organizationId: Int,
    getOrganizationViewModel: OrganizationViewModel = hiltViewModel(),
    tokenViewModel: TokenViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val organizationResult =
        getOrganizationViewModel.organizationResultFlow.collectAsStateWithLifecycle()

    tokenViewModel.authToken?.let {
        getOrganizationViewModel.getOrganization(organizationId.toString(), it)
        GetOrganizationData(organizationResult, navController)
    }

}


@Composable
fun GetOrganizationData(
    organizationResult: State<PetFinderResult<GetOrganizationResponse>>,
    navController: NavHostController
) {

    when (val organizationData = organizationResult.value) {

        is PetFinderResult.Failure -> Log.d(
            TAG,
            "GetOrganizationData: GetData Organization Result: ${organizationData.errorMessage}"

        )

        is PetFinderResult.Loading -> CircularProgressIndicator()
        is PetFinderResult.Success -> {
            Log.d(TAG, "GetOrganizationData: ${organizationData.data}")
            ShowOrganizationData(organizationData.data)
        }

        else -> Log.d(TAG, "GetOrganizationData: State None")
    }
}

@Composable
private fun ShowOrganizationData(data: GetOrganizationResponse) {

    val bottomPaddingLayout = if (isThreeButtonNavSystem()) 50.dp else 10.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 20.dp, end = 20.dp)
                .background(color = Color.Green),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.arrow_back),
                contentDescription = null,
                modifier = Modifier
                    //.padding(top = 50.dp, start = 20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = colorResource(R.color.off_white))
                    .padding(10.dp)
            )

            Text(
                text = "Organization Details",
                Modifier.padding(start = 40.dp),
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily(Font(R.font.dmsans)),
                color = colorResource(R.color.off_black_text)
            )
        }

        Image(
            painter = rememberAsyncImagePainter(model = "https://dl5zpyw5k3jeb.cloudfront.net/organization-photos/48548/6/?bust=1541627660"),
            contentDescription = null,
            modifier = Modifier
                .padding(15.dp)
                .background(color = Color.Magenta)
                .size(150.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .clip(
                    RoundedCornerShape(20.dp)
                )
                .background(color = Color.White),
        ) {

            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Fayette",
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.dmsans)),
                    fontWeight = FontWeight.ExtraBold
                )
                IconWithText(
                    icon = painterResource(R.drawable.call_ringing),
                    text = "(404) 434-5345"
                )
                IconWithText(
                    icon = painterResource(R.drawable.all_mails),
                    text = "jameslovescoding@gmail.com"
                )
                IconWithText(
                    icon = painterResource(R.drawable.map_marker_nearby),
                    text = "1242 South ClubHouse Circle"
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                .clip(
                    RoundedCornerShape(20.dp)
                )
                .background(color = Color.White),
        ) {

            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Text(
                    text = "Working Days",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.dmsans)),
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "Monday - Friday",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.dmsans)),
                    color = colorResource(R.color.light_grey_text)
                )
                Text(
                    text = "Mission Statement",
                    modifier = Modifier.padding(top = 10.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.dmsans)),
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "Incorporated in 1973, the Fayette Humane Society (FHS) is the oldest nonprofit humane organization in Fayette County, Georgia.",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.dmsans)),
                    color = colorResource(R.color.light_grey_text),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                .clip(
                    RoundedCornerShape(20.dp)
                )
                .background(color = Color.White),
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly

            ) {
                Image(
                    painter = painterResource(R.drawable.facebook),
                    contentDescription = null,
                    modifier = Modifier
                        //.padding(top = 50.dp, start = 20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = colorResource(R.color.light_purple_background))
                        .padding(10.dp)
                )

                Image(
                    painter = painterResource(R.drawable.tiktok),
                    contentDescription = null,
                    modifier = Modifier
                        //.padding(top = 50.dp, start = 20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = colorResource(R.color.light_purple_background))
                        .padding(10.dp)
                )

                Image(
                    painter = painterResource(R.drawable.instagram),
                    contentDescription = null,
                    modifier = Modifier
                        //.padding(top = 50.dp, start = 20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = colorResource(R.color.light_purple_background))
                        .padding(10.dp)
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                .clip(
                    RoundedCornerShape(20.dp)
                )
                .background(color = Color.White),
        ) {

            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .background(color = Color.Green)
            ) {
                Text(
                    text = "Photos",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.dmsans)),
                    fontWeight = FontWeight.ExtraBold
                )
                LazyRow(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp)
                ) {
                    items(listOf<String>()) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = if (1 != 0) {
                                    "data.animal?.photos?.get(0)?.full" ?: PLACE_HOLDER
                                } else {
                                    PLACE_HOLDER
                                }
                            ),
                            contentDescription = null,
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun IconWithText(
    icon: Painter, // or Painter for custom icons
    text: String,
) {

    Row(
        modifier = Modifier.padding(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = icon,
            contentDescription = null,
            tint = colorResource(R.color.light_grey_text),
            modifier = Modifier.size(10.dp)
        )
        Spacer(modifier = Modifier.size(2.dp))
        Text(
            text = text,
            fontSize = 11.sp,
            color = colorResource(R.color.light_grey_text),
            fontFamily = FontFamily(
                Font(R.font.dmsans)
            )
        )
    }
}

@Preview
@Composable
fun ShowOrganizationDataPreview(data: GetOrganizationResponse.Organization? = null) {
    ShowOrganizationData(GetOrganizationResponse())
}