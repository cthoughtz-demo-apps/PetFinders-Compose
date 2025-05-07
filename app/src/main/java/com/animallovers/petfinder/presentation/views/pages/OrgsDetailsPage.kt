package com.animallovers.petfinder.presentation.views.pages

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
    organizationId: String,
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
            ShowOrganizationData(organizationData.data, navController)
        }

        else -> Log.d(TAG, "GetOrganizationData: State None")
    }
}

@Composable
private fun ShowOrganizationData(data: GetOrganizationResponse, navController: NavHostController) {

    val bottomPaddingLayout = if (isThreeButtonNavSystem()) 50.dp else 10.dp
    val screenConfiguration = LocalConfiguration.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue)
            .padding(bottom = bottomPaddingLayout),
        //horizontalAlignment = Alignment.CenterHorizontally,

    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 20.dp, end = 20.dp),
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
                    .clickable {
                        navController.popBackStack()
                    }
            )

            Text(
                text = "Organization Details",
                Modifier.padding(start = 40.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily(Font(R.font.dmsans)),
                color = colorResource(R.color.white)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = rememberAsyncImagePainter(
                    model = if (data.organization?.photos?.size != 0) {
                        data.organization?.photos?.get(0)?.full ?: PLACE_HOLDER
                    } else {
                        PLACE_HOLDER
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(15.dp)
                    .size(
                        width = screenConfiguration.screenWidthDp.dp * 0.40f,
                        height = screenConfiguration.screenHeightDp.dp * 0.20f
                    ),
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
                        text = data.organization?.name ?: "N/A",
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.dmsans)),
                        fontWeight = FontWeight.ExtraBold
                    )
                    IconWithText(
                        icon = painterResource(R.drawable.call_ringing),
                        text = data.organization?.phone ?: "N/A"
                    )
                    IconWithText(
                        icon = painterResource(R.drawable.all_mails),
                        text = data.organization?.email ?: "N/A"
                    )
                    IconWithText(
                        icon = painterResource(R.drawable.map_marker_nearby),
                        text = "${data.organization?.address?.address1 ?: "N/A"}, ${data.organization?.address?.city ?: "N/A"}, ${data.organization?.address?.postcode ?: "N/A"}, ${data.organization?.address?.country ?: "N/A"}"
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
                        text = if (data.organization?.missionStatement.toString().isNullOrEmpty()) {
                            data.organization?.missionStatement.toString()
                        } else {
                            "N/A"
                        },
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

                    SocialMedia(R.drawable.facebook, data.organization?.socialMedia?.facebook)
                    SocialMedia(R.drawable.tiktok, data.organization?.socialMedia?.youtube)
                    SocialMedia(R.drawable.instagram, data.organization?.socialMedia?.instagram)
                }
            }
        }
    }
}

@Composable
fun SocialMedia(image: Int, url: String?) {

    val context = LocalContext.current
    Log.d(TAG, "SocialMedia Url: $url")

    Image(
        painter = painterResource(image),
        contentDescription = null,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color = colorResource(R.color.light_purple_background))
            .padding(top = 10.dp, start = 10.dp, end = 7.dp, bottom = 7.dp)
            .clickable {
                url?.let {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }
            }
    )

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
    ShowOrganizationData(
        GetOrganizationResponse(),
        navController = NavHostController(LocalContext.current)
    )
}