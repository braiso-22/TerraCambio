import clock.FixedClock
import com.github.braiso_22.chat.Chat
import com.github.braiso_22.chat.vo.ChatId
import com.github.braiso_22.chat.vo.MessageContent
import com.github.braiso_22.chat.vo.MessageId
import com.github.braiso_22.chat.vo.UserId
import kotlin.test.*
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
class ChatTest {

    private val userA = UserId.example()
    private val userB = UserId(Uuid.random())
    private val fixedInstant = Instant.parse("2025-10-26T10:00:00Z")
    private lateinit var clock: FixedClock

    @BeforeTest
    fun setup() {
        clock = FixedClock(fixedInstant)
    }


    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `addMessage should create a message and update lastMessageTimestamp`() {
        val chat = Chat(
            id = ChatId.example(),
            participants = setOf(userA, userB),
            lastMessageTimestamp = null
        )

        val messageId = MessageId.example()
        val content = MessageContent.Text("Hello")


        val message = chat.addMessage(
            authorId = userA,
            content = content,
            messageId = messageId,
            clock = clock
        )

        assertEquals(messageId, message.id)
        assertEquals(userA, message.authorId)
        assertEquals(content, message.content)
        assertNotNull(message.timestamp)
        assertEquals(chat.lastMessageTimestamp, message.timestamp)
    }

    @Test
    fun `addMessage should reject non-participant authors`() {
        val chat = Chat(
            id = ChatId(Uuid.random()),
            participants = setOf(userA),
            lastMessageTimestamp = null
        )

        val messageId = MessageId(Uuid.random())
        val content = MessageContent.Text("Hi!")

        assertFailsWith<IllegalArgumentException> {
            chat.addMessage(
                authorId = userB, // not a participant
                content = content,
                messageId = messageId,
                clock = clock
            )
        }
    }


    @Test
    fun `lastMessageTimestamp should remain null until first message`() {
        val chat = Chat(
            id = ChatId(Uuid.random()),
            participants = setOf(userA, userB),
            lastMessageTimestamp = null
        )

        assertNull(chat.lastMessageTimestamp)

        chat.addMessage(
            authorId = userA,
            content = MessageContent.Text("first"),
            messageId = MessageId(Uuid.random()),
            clock = clock
        )

        assertNotNull(chat.lastMessageTimestamp)
    }

    @Test
    fun `each message should have increasing timestamp`() {
        val chat = Chat(
            id = ChatId(Uuid.random()),
            participants = setOf(userA, userB),
            lastMessageTimestamp = null
        )

        val m1 = chat.addMessage(
            authorId = userA,
            content = MessageContent.Text("first"),
            messageId = MessageId(Uuid.random()),
            clock = clock
        )
        clock.add(1.seconds)
        val m2 = chat.addMessage(
            authorId = userB,
            content = MessageContent.Text("second"),
            messageId = MessageId(Uuid.random()),
            clock = clock
        )

        assertTrue(m2.timestamp.value.toEpochMilliseconds() > m1.timestamp.value.toEpochMilliseconds())
        assertEquals(chat.lastMessageTimestamp, m2.timestamp)
    }
}