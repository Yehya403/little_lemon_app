package com.example.littlelemon

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.littlelemon.ui.theme.PrimaryGreen
import com.example.littlelemon.ui.theme.PrimaryYellow


@Composable
fun Home(navController: NavController) {
    val viewModel: MenuViewModel = viewModel()
    val databaseMenuItems = viewModel.getAllDatabaseMenuItems().observeAsState(emptyList()).value

    LaunchedEffect(Unit) {
        viewModel.fetchMenuDataIfNeeded()
    }

    val searchPhrase = remember {
        mutableStateOf("")
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(70.dp)
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                )

                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile icon",
                    modifier = Modifier
                        .size(50.dp)
                        .clickable { navController.navigate(Profile.route) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Column(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
                    .background(PrimaryGreen)
            ) {
                Text(
                    text = "Little Lemon",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold, color = PrimaryYellow
                )
                Text(
                    text = "Chicago",
                    fontSize = 24.sp, color = Color.White
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                ) {
                    Text(
                        text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .padding(bottom = 20.dp, end = 20.dp)
                            .fillMaxWidth(0.6f), color = Color.White
                    )
                    Image(
                        painter = painterResource(id = R.drawable.hero_image),
                        contentDescription = "Upper Panel Image",
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .size(200.dp)
                    )
                }

                TextField(
                    value = searchPhrase.value,
                    onValueChange = { searchPhrase.value = it },
                    placeholder = { Text("Enter search phrase",color= Color.Black)},
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp).background(Color.White)
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        val filteredMenuItems = if (searchPhrase.value.isBlank()) {
            databaseMenuItems
        } else {
            databaseMenuItems.filter { menuItem ->
                menuItem.title.contains(searchPhrase.value, ignoreCase = true)
            }
        }
        items(filteredMenuItems) {menuItem ->
            MenuItem(item = menuItem)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Home(navController = rememberNavController())
}

@Composable
fun MenuItem(item: MenuItemRoom) {
    Divider(color = Color.Gray, thickness = 1.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxWidth(0.5f)) {
            Text(text = item.title, fontWeight = FontWeight.Bold)
            Text(text = "$${item.price}")
            Text(text = item.description)
        }
        Column {
            AsyncImage(
                model = item.image,
                contentDescription = "meal_image",
                modifier = Modifier.size(100.dp, 100.dp),
                contentScale = ContentScale.Crop,
            )
        }

    }
}
