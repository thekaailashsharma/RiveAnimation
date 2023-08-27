package anim.rive.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import anim.rive.compose.ui.theme.P2PBackground
import anim.rive.compose.ui.theme.TextColor
import anim.rive.compose.ui.theme.monteSB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginUI() {
    var password by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var email by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    var isChecking by remember { mutableStateOf(false) }
    var isCheckingJob: Job? = null // Initialize isCheckingJob
    var trigSuccess by remember { mutableStateOf(false) }
    var trigFail by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(P2PBackground)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(1f)
                .background(P2PBackground),
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize(0.8f)
                    .align(Alignment.Center)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ComposableRiveAnimationView(
                        animation = R.raw.login,
                        modifier = Modifier
                            .size(400.dp)
                    ) { view ->
                        view.setBooleanState(
                            "Login Machine",
                            "isHandsUp",
                            passwordVisible.value
                        )
                        view.setBooleanState(
                            "Login Machine",
                            "isChecking",
                            isChecking
                        )
                        if (trigFail)
                            view.fireState("Login Machine", "trigFail")
                        if (trigSuccess)
                            view.fireState("Login Machine", "trigSuccess")
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize(0.5f)
                    .padding(start = 40.dp, bottom = 20.dp, end = 40.dp)
                    .align(Alignment.BottomCenter),
                colors = CardDefaults.cardColors(Color(0xFFFFFFFF)),
                shape = RoundedCornerShape(15.dp),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 35.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF4885ED),
                                fontFamily = monteSB,
                            )
                        ) {
                            append("Welcome")
                        }
                        append(" ")
                        withStyle(
                            SpanStyle(
                                color = Color(0xFFF4C20D).copy(0.89f),
                                fontFamily = monteSB
                            )
                        ) {
                            append("to")
                        }
                    }, fontSize = 25.sp)
                    Text(text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF1DE9B6),
                                fontFamily = monteSB,
                            )
                        ) {
                            append("Rive")
                        }
                        append(" ")
                    }, fontSize = 25.sp)
                    Spacer(modifier = Modifier.height(30.dp))
                    TextFieldWithIcons(
                        textValue = "Email ID",
                        placeholder = "Enter Your Email",
                        icon = Icons.Filled.Email,
                        mutableText = email,
                        onValueChanged = {
                            email = it
                        },
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    TextFieldWithIcons(
                        textValue = "Password",
                        placeholder = "Enter Your Password",
                        icon = Icons.Filled.Lock,
                        mutableText = password,
                        onValueChanged = {
                            password = it
                            if (isCheckingJob?.isActive == true) {
                                isCheckingJob?.cancel()
                            }
                            isCheckingJob = CoroutineScope(Dispatchers.Main).launch {
                                delay(1000) // Adjust the delay time as needed
                                isChecking = false
                            }
                            isChecking = true
                        },
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next,
                        passwordVisible = passwordVisible
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        onClick = {
                            if (password.text == "123456" && email.text == "abc@gmail.com") {
                                trigSuccess = true
                                trigFail = false
                            } else {
                                trigSuccess = false
                                trigFail = true
                            }

                        }
                    ) {
                        Text(
                            text = "Login",
                            color = Color.White,
                            fontSize = 20.sp,
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }

            }

        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp, vertical = 30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.rive),
                    tint = Color.Unspecified,
                    contentDescription = "Icon",
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    Icons.Filled.MoreHoriz,
                    tint = Color(0xFF6297F1),
                    contentDescription = "Icon",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    painter = painterResource(id = R.drawable.compose),
                    tint = Color.Unspecified,
                    contentDescription = "Icon",
                    modifier = Modifier.size(60.dp)
                )

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldWithIcons(
    textValue: String,
    placeholder: String,
    icon: ImageVector,
    mutableText: TextFieldValue,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    passwordVisible: MutableState<Boolean> = mutableStateOf(false),
    onValueChanged: (TextFieldValue) -> Unit,
) {
    if (keyboardType == KeyboardType.Password) {
        TextField(
            value = mutableText,
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    tint = Color(0xFF4483D1),
                    contentDescription = "Icon"
                )
            },
            trailingIcon = {
                val image = if (passwordVisible.value)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description = if (passwordVisible.value) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(imageVector = image, description, tint = Color(0xFF4483D1))
                }
            },
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            onValueChange = onValueChanged,
            label = { Text(text = textValue, color = TextColor) },
            placeholder = { Text(text = placeholder, color = TextColor) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            modifier = Modifier
                .padding(start = 15.dp, top = 5.dp, bottom = 5.dp, end = 15.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = P2PBackground,
                textColor = TextColor
            )
        )
    } else {
        TextField(
            value = mutableText,
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    tint = Color(0xFF4483D1),
                    contentDescription = "Icon"
                )
            },
            onValueChange = onValueChanged,
            label = { Text(text = textValue, color = TextColor) },
            placeholder = { Text(text = placeholder, color = TextColor) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            modifier = Modifier
                .padding(start = 15.dp, top = 5.dp, bottom = 5.dp, end = 15.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = P2PBackground,
                textColor = TextColor
            ),

            )
    }
}
