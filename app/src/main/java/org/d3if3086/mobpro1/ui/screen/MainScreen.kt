package org.d3if3086.mobpro1.ui.screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3086.mobpro1.R
import org.d3if3086.mobpro1.model.Bakso
import org.d3if3086.mobpro1.navigation.Screen
import org.d3if3086.mobpro1.ui.theme.MobPro1Theme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobPro1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(rememberNavController(), viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }, colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(id = R.color.Light_Coral),
                    titleContentColor = colorResource(id = R.color.Saddle_Brown)
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.About.route)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.tentang_aplikasi),
                            tint = colorResource(id = R.color.Saddle_Brown)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.Add.route)
                },
                containerColor = colorResource(id = R.color.Light_Coral)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.tambah_catatan),
                    tint = colorResource(id = R.color.Saddle_Brown)
                )
            }
        }
    ) { paddingValues ->
        ScreenContent(modifier = Modifier.padding(paddingValues), viewModel = viewModel)
    }
}

@Composable
fun ScreenContent(modifier: Modifier, viewModel: MainViewModel) {
    val data = viewModel.data
    val context = LocalContext.current

    if (viewModel.nama.isNotEmpty()) {
        viewModel.addBakso(viewModel.nama, viewModel.deskripsi, viewModel.review)
        viewModel.nama = ""
        viewModel.deskripsi = ""
        viewModel.review = ""

    }

    if (data.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.list_kosong))
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 84.dp)
        ) {
            items(data) { bakso ->
                ListItem(
                    bakso = bakso,
                    onClick = {
                        val pesan = context.getString(R.string.x_diklik, bakso.nama)
                        Toast.makeText(context, pesan, Toast.LENGTH_SHORT).show()
                    },
                    onDelete = { viewModel.deleteBakso(bakso) },
                    onShareClick = {
                        shareData(
                            context,
                            context.getString(
                                R.string.bagikan_template,
                                bakso.nama,
                                bakso.deskripsi,
                                bakso.review
                            )
                        )
                    }
                )
                Divider()
            }
        }
    }
}

@Composable
fun ListItem(bakso: Bakso, onClick: () -> Unit, onDelete: () -> Unit, onShareClick: () -> Unit) {

    val customButtonColors = ButtonDefaults.buttonColors(
        containerColor = colorResource(id = R.color.Light_Coral),
        contentColor = colorResource(id = R.color.Saddle_Brown)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onClick() },
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Name : ")
                    }
                    append(bakso.nama)
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(id = R.string.delete),
                modifier = Modifier.clickable { onDelete() },
            )
        }
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("description : ")
                }
                append(bakso.deskripsi)
            },
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("review : ")
                }
                append(bakso.review)
            }
        )
        ElevatedButton(
            onClick =
            onShareClick,
            colors = customButtonColors
        ) {
            Text(text = stringResource(R.string.bagikan))
        }
    }
}

private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MobPro1Theme {
        MainScreen(rememberNavController(), viewModel())
    }
}