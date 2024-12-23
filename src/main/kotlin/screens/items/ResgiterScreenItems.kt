package screens.items

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import kotlin.math.absoluteValue

@Composable


fun PhoneInputField(phoneNumber: String, onPhoneChange: (String) -> Unit) {
    var phoneInput by remember { mutableStateOf(phoneNumber) }

    OutlinedTextField(
        value = phoneInput,
        onValueChange = { newInput ->
            // Only allow digits and limit to 9 digits to fit the format (+998 99 888 88 88)
            val filtered = newInput.filter { it.isDigit() }.take(9)
            phoneInput = filtered
            onPhoneChange(phoneInput) // Notify the parent with the updated value
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Enter Phone Number") },
        visualTransformation = MaskVisualTransformation("+998-##-###-##-##")
    )
}



class MaskVisualTransformation(private val mask: String): VisualTransformation {
    private val specialSymbolsIndices = mask.indices.filter { mask[it] != '#' }

    override fun filter(text: AnnotatedString): TransformedText {
        var out = ""
        var maskIndex = 0

        text.forEach { char ->
            while (specialSymbolsIndices.contains(maskIndex)) {
                out += mask[maskIndex]
                maskIndex++
            }
            out += char
            maskIndex++
        }
        return TransformedText(AnnotatedString(out), offsetTranslator())
    }

    private fun offsetTranslator() = object: OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            val offsetValue = offset.absoluteValue
            if (offsetValue == 0) return 0
            var numberOfHashtags = 0
            val masked = mask.takeWhile {
                if (it == '#') numberOfHashtags++
                numberOfHashtags < offsetValue
            }
            return masked.length + 1
        }

        override fun transformedToOriginal(offset: Int): Int {
            return mask.take(offset.absoluteValue).count { it == '#' }
        }
    }
}




//date
@Composable
fun DateInputField(onDateChange: (String) -> Unit) {
    var dateInput by remember { mutableStateOf("") }

    OutlinedTextField(
        value = dateInput,
        onValueChange = { newInput ->
            onDateChange(newInput)
            val filtered = newInput.filter { it.isDigit() } // Only allow numbers
            dateInput = filtered.take(8) // Restrict to 8 characters (DDMMYYYY)
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Enter Date (DD/MM/YYYY)") },
        visualTransformation = DateVisualTransformation()
    )
}


class DateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val input = text.text
        val transformedText = buildString {
            input.forEachIndexed { index, char ->
                if (index == 2 || index == 4) append("/")
                append(char)
            }
        }.take(10) // Enforce "DD/MM/YYYY" max length

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 2 -> offset
                    offset <= 4 -> offset + 1
                    offset <= 8 -> offset + 2
                    else -> 10
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 2 -> offset
                    offset <= 5 -> offset - 1
                    offset <= 10 -> offset - 2
                    else -> 8
                }
            }
        }

        return TransformedText(AnnotatedString(transformedText), offsetMapping)
    }
}
