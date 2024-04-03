package org.d3if3086.mobpro1.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3086.mobpro1.R
import org.d3if3086.mobpro1.ui.theme.MobPro1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavHostController, viewModel: MainViewModel) {
    var nama by rememberSaveable { mutableStateOf("") }
    var deskripsi by rememberSaveable { mutableStateOf("") }
    var review by rememberSaveable { mutableStateOf("") }

    Scaffold(topBar = {
        TopAppBar(navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.kembali),
                    tint = colorResource(id = R.color.Saddle_Brown)
                )
            }
        }, title = {
            Text(text = stringResource(id = R.string.app_name))
        }, colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = colorResource(id = R.color.Light_Coral),
            titleContentColor = colorResource(id = R.color.Saddle_Brown)
        )
        )
    }) { paddingValues ->
        AddBaksoContent(
            navController = navController,
            viewModel = viewModel,
            nama = nama,
            deskripsi = deskripsi,
            review = review,
            onNamaChange = { nama = it },
            onDeskripsiChange = { deskripsi = it },
            onReviewChange = { review = it },
            modifier = Modifier.padding(paddingValues)
        )

    }
}

@Composable
fun AddBaksoContent(
    navController: NavController,
    viewModel: MainViewModel,
    nama: String,
    deskripsi: String,
    review: String,
    onNamaChange: (String) -> Unit,
    onDeskripsiChange: (String) -> Unit,
    onReviewChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showError by rememberSaveable { mutableStateOf(false) }

    val customButtonColors = ButtonDefaults.buttonColors(
        containerColor = colorResource(id = R.color.Light_Coral),
        contentColor = colorResource(id = R.color.Saddle_Brown)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = nama,
            onValueChange = {
                onNamaChange(it)
                showError = false
            },
            label = {
                Text(
                    text = stringResource(R.string.nama_bakso),
                    color = colorResource(id = R.color.Saddle_Brown)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {}),
            colors = OutlinedTextFieldDefaults.colors(

                cursorColor = colorResource(id = R.color.Light_Coral),
                focusedBorderColor = colorResource(id = R.color.Light_Coral),
                unfocusedBorderColor = colorResource(id = R.color.Light_Coral),
            )
        )
        OutlinedTextField(
            value = deskripsi,
            onValueChange = {
                onDeskripsiChange(it)
                showError = false
            },
            label = {
                Text(
                    text = stringResource(R.string.deskripsi_bakso),
                    color = colorResource(id = R.color.Saddle_Brown)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {}),
            colors = OutlinedTextFieldDefaults.colors(

                cursorColor = colorResource(id = R.color.Light_Coral),
                focusedBorderColor = colorResource(id = R.color.Light_Coral),
                unfocusedBorderColor = colorResource(id = R.color.Light_Coral),
            )
        )
        OutlinedTextField(
            value = review,
            onValueChange = {
                onReviewChange(it)
                showError = false
            },
            label = {
                Text(
                    text = stringResource(R.string.review_bakso),
                    color = colorResource(id = R.color.Saddle_Brown)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {}),
            colors = OutlinedTextFieldDefaults.colors(

                cursorColor = colorResource(id = R.color.Light_Coral),
                focusedBorderColor = colorResource(id = R.color.Light_Coral),
                unfocusedBorderColor = colorResource(id = R.color.Light_Coral),
            )
        )
        if (showError) {
            Text(
                text = stringResource(R.string.input_invalid),
                color = colorResource(id = R.color.Saddle_Brown),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Button(
            onClick = {
                if (nama.isNotEmpty() && deskripsi.isNotEmpty() && review.isNotEmpty()) {
                    viewModel.addBakso(nama, deskripsi, review)
                    navController.popBackStack()
                } else {
                    showError = true
                }
            },
            modifier = Modifier.padding(top = 16.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
            colors = customButtonColors
        ) {
            Text(text = stringResource(R.string.masukkan))
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AddScreenPreview() {
    MobPro1Theme {
        AddScreen(rememberNavController(), viewModel())
    }
}