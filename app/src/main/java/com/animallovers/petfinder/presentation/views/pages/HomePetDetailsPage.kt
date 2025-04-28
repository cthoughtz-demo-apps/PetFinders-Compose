package com.animallovers.petfinder.presentation.views.pages

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.animallovers.petfinder.R
import com.animallovers.petfinder.domain.model.animal.GetAnimalResponse
import com.animallovers.petfinder.presentation.util.Constants.PLACE_HOLDER
import com.animallovers.petfinder.presentation.util.Contact
import com.animallovers.petfinder.presentation.util.CurvedBottomRectangle
import com.animallovers.petfinder.presentation.util.PetFinderResult
import com.animallovers.petfinder.presentation.util.isThreeButtonNavSystem
import com.animallovers.petfinder.presentation.viewmodel.GetAnimalViewModel
import com.animallovers.petfinder.presentation.viewmodel.TokenViewModel

private const val TAG = "HomePetDetailsPage"

@Composable
fun HomePetDetailsPage(
    animalId: Int,
    getAnimalViewModel: GetAnimalViewModel = hiltViewModel(),
    tokenViewModel: TokenViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val animalResult = getAnimalViewModel.getAnimalResultFlow.collectAsStateWithLifecycle()

    tokenViewModel.authToken?.let {
        getAnimalViewModel.getAnimal(animalId.toString(), it)
        GetData(animalResult, navController)
    }
}

@Composable
fun GetData(
    animalResult: State<PetFinderResult<GetAnimalResponse>>,
    navController: NavHostController
) {
    when (val animalData = animalResult.value) {
        is PetFinderResult.Failure -> Log.d(
            TAG,
            "GetData Animal Result: ${animalData.errorMessage}"
        )

        // TODD -> Put CircularProgressIndicator in the middle of the screen
        is PetFinderResult.Loading -> CircularProgressIndicator()
        is PetFinderResult.Success -> {
            Log.d(TAG, "GetData Animal Result: ${animalData.data}")
            ShowDisplay(animalData.data, navController)
        }

        else -> Log.d(TAG, "GetData Animal Result: State None")
    }
}

@Composable
fun ShowDisplay(data: GetAnimalResponse, navController: NavHostController) {

    val bottomPaddingLayout = if (isThreeButtonNavSystem()) 50.dp else 10.dp

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cyan)
    ) {

        val (box, animalName, animalInfoRow, aboutLabel, description, contactRow) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxSize(fraction = 0.5f)
                .fillMaxWidth()
                .clip(CurvedBottomRectangle(200f))
                .background(color = Color.Green) // Keep this until the ui is complete to know how everything is spaced
                .constrainAs(box) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        ) {

            Image(
                painter = rememberAsyncImagePainter(
                    model = if (data.animal?.photos?.size != 0) {
                        data.animal?.photos?.get(0)?.full ?: PLACE_HOLDER
                    } else {
                        PLACE_HOLDER
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        navController.popBackStack()
                    }
            )


            Image(
                painter = painterResource(R.drawable.arrow_back),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 50.dp, start = 20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = colorResource(R.color.off_white))
                    .padding(10.dp)
            )

        }

        Text(
            text = data.animal?.name ?: "Bello",
            fontSize = 23.sp,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily(Font(R.font.dmsans)),
            modifier = Modifier.constrainAs(animalName) {
                top.linkTo(box.bottom, margin = 10.dp)
                start.linkTo(parent.start, margin = 20.dp)
            })

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .background(color = Color.Yellow)
                .constrainAs(animalInfoRow) {
                    top.linkTo(animalName.bottom, margin = 5.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                    width = Dimension.fillToConstraints
                }
        ) {
            AnimalDisplayCard(
                modifier = Modifier.weight(1f),
                backgroundColor = colorResource(R.color.tan_color_background),
                headerText = data.animal?.age ?: "N/A",
                subText = "Age"
            )
            AnimalDisplayCard(
                modifier = Modifier.weight(1f),
                backgroundColor = colorResource(R.color.pink_color_background),
                headerText = data.animal?.gender ?: "N/A",
                subText = "Gender"
            )
            AnimalDisplayCard(
                modifier = Modifier.weight(1f),
                backgroundColor = colorResource(R.color.pinkish_color_background),
                headerText = data.animal?.size ?: "N/A",

                subText = "Weight"
            )

        }

        Text(
            text = "About",
            fontSize = 23.sp,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily(Font(R.font.dmsans)),
            modifier = Modifier.constrainAs(aboutLabel) {
                top.linkTo(animalInfoRow.bottom, margin = 10.dp)
                start.linkTo(parent.start, margin = 20.dp)
            })

        Text(
            text = data.animal?.description ?: "N/A",
            fontSize = 13.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            color = colorResource(R.color.light_grey_text),
            fontFamily = FontFamily(Font(R.font.dmsans)),
            modifier = Modifier.constrainAs(description) {
                top.linkTo(aboutLabel.bottom, margin = 5.dp)
                start.linkTo(parent.start, margin = 20.dp)
                end.linkTo(parent.end, margin = 20.dp)
                width = Dimension.fillToConstraints
            })

        Row(
            modifier = Modifier
                .background(color = Color.Yellow)
                .padding(5.dp)
                .constrainAs(contactRow) {
                    bottom.linkTo(parent.bottom, margin = bottomPaddingLayout)
                    start.linkTo(parent.start, margin = 20.dp)
                }
        ) {
            CustomIcon(R.drawable.call_ringing, Contact.PHONE, data)
            CustomIcon(R.drawable.all_mails, Contact.EMAIL, data)
            CustomIcon(R.drawable.map_marker_nearby, Contact.Location, data)
        }
    }
}

@Composable
fun CustomIcon(id: Int, contact: Contact, animalData: GetAnimalResponse) {

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            animalData.animal?.contact?.phone?.let { phoneNumber ->
                makePhoneCall(context, phoneNumber)
            }
        }
    }

    Icon(
        painter = painterResource(id),
        contentDescription = null,
        modifier = Modifier
            .padding(end = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = colorResource(R.color.icon_background))
            .padding(10.dp)
            .clickable {
                when (contact) {
                    Contact.PHONE -> {
                        animalData.animal?.contact?.phone?.let { phoneNumber ->

                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CALL_PHONE
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                makePhoneCall(context, phoneNumber)
                            } else {
                                launcher.launch(Manifest.permission.CALL_PHONE)
                            }

                        }
                    }

                    Contact.EMAIL -> {
                        animalData.animal?.contact?.email?.let { email ->
                            sendEmail(
                                context,
                                email,
                                subject = "More Info",
                                body = """
                                    Hello, 
                                    I am interested in finding out more information about:
                                    Name: ${animalData.animal?.name} 
                                    Id: ${animalData.animal?.id} 
                                    Photo: ${animalData.animal?.photos?.get(0)?.small}. 
                                    Thanks""".trimIndent()
                            )
                        }
                    }

                    Contact.Location -> {
                        animalData.animal?.contact?.address?.let { address ->
                            showOnMap(
                                context = context,
                                address = "${address.address1}, ${address.state}, ${address.city}, ${address.state}"
                            )
                        }
                    }
                }
            },
        tint = colorResource(R.color.purple)
    )
}


private fun showOnMap(
    context: Context,
    address: String
) {

    val encodedAddress = Uri.encode(address)
    val url = "https://www.google.com/maps/search/?api=1&query=$encodedAddress"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "No Map", Toast.LENGTH_SHORT).show()
    }
}

private fun sendEmail(
    context: Context,
    email: String,
    subject: String,
    body: String
) {

    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }

    try {
        context.startActivity(Intent.createChooser(intent, "Send Email"))
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "No email app installed", Toast.LENGTH_SHORT).show()
    }
}


private fun makePhoneCall(context: Context, phoneNumber: String) {
    val cleanNumber = phoneNumber.replace("[^0-9+]".toRegex(), "")
    val intent = Intent(Intent.ACTION_CALL).apply {
        data = Uri.parse("tel:$cleanNumber")
    }
    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "No phone app found", Toast.LENGTH_SHORT).show()
    } catch (e: SecurityException) {
        Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun AnimalDisplayCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    headerText: String,
    subText: String
) {
    Card(
        modifier = modifier
            .height(65.dp)
            .wrapContentWidth()
            .widthIn(max = 115.dp)
            .padding(2.dp)
            .background(color = Color.Green)
            .clip(RoundedCornerShape(20.dp)),
        backgroundColor = backgroundColor,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,

            ) {
            Text(
                text = headerText,
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.dmsans))
            )
            Text(
                text = subText,
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.dmsans)),
                color = colorResource(R.color.light_grey_text)
            )
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ShowDisplayPagePreview(modifier: Modifier = Modifier) {
    ShowDisplay(GetAnimalResponse(), navController = NavHostController(LocalContext.current))
}
