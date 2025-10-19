import com.github.braiso_22.chat.vo.MessageContent
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class MessageContentTest {

    @Test
    fun `Text should reject blank text`() {
        assertFailsWith<IllegalArgumentException> {
            MessageContent.Text("")
        }
        assertFailsWith<IllegalArgumentException> {
            MessageContent.Text("   ")
        }
    }

    @Test
    fun `Image should reject blank imageUrl`() {
        assertFailsWith<IllegalArgumentException> {
            MessageContent.Image.create("", null)
        }
        assertFailsWith<IllegalArgumentException> {
            MessageContent.Image.create("   ", "caption")
        }
    }

    @Test
    fun `Image should allow null caption`() {
        val image = MessageContent.Image.create("https://example.com/pic.png", null)
        assertEquals("https://example.com/pic.png", image.imageUrl)
        assertNull(image.caption)
    }

    @Test
    fun `Image should convert empty caption to null`() {
        val image = MessageContent.Image.create("https://example.com/pic.png", "")
        assertNull(image.caption)
    }

    @Test
    fun `Image should keep non-blank caption`() {
        val image = MessageContent.Image.create("https://example.com/pic.png", "nice photo")
        assertEquals("nice photo", image.caption)
    }

    @Test
    fun `TextAndImage should reject blank text or url`() {
        assertFailsWith<IllegalArgumentException> {
            MessageContent.TextAndImage("", "https://example.com/x.png")
        }
        assertFailsWith<IllegalArgumentException> {
            MessageContent.TextAndImage("Hello", " ")
        }
    }
}