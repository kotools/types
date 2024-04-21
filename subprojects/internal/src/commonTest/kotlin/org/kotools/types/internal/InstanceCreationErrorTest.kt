package org.kotools.types.internal

import kotools.types.internal.InternalKotoolsTypesApi
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

@OptIn(InternalKotoolsTypesApi::class)
class InstanceCreationErrorTest {
    @Test
    fun constructor_should_pass_with_valid_arguments() {
        val typeQualifiedName = "org.kotools.types.Zero"
        val invalidValue: Any = Random.nextInt(1..Int.MAX_VALUE)
        val reason = "It should be equal to the zero number."
        InstanceCreationError(typeQualifiedName, invalidValue, reason)
    }

    @Test
    fun constructor_should_fail_with_a_blank_typeQualifiedName() {
        val typeQualifiedName = "  "
        val invalidValue: Any = Random.nextInt(1..Int.MAX_VALUE)
        val reason = "It should be equal to the zero number."
        val exception: IllegalArgumentException = assertFailsWith {
            InstanceCreationError(typeQualifiedName, invalidValue, reason)
        }
        val actual: String = assertNotNull(
            actual = exception.message,
            message = "Exception's message shouldn't be null."
        )
        val expected: String = InstanceCreationError.BLANK_QUALIFIED_NAME
        assertEquals(expected, actual)
    }

    @Test
    fun constructor_should_fail_with_a_blank_reason() {
        val typeQualifiedName = "org.kotools.types.Zero"
        val invalidValue: Any = Random.nextInt(1..Int.MAX_VALUE)
        val reason = "  "
        val exception: IllegalArgumentException = assertFailsWith {
            InstanceCreationError(typeQualifiedName, invalidValue, reason)
        }
        val actual: String = assertNotNull(
            actual = exception.message,
            message = "Exception's message shouldn't be null."
        )
        val expected: String = InstanceCreationError.BLANK_REASON
        assertEquals(expected, actual)
    }

    @Test
    fun toString_should_pass() {
        val typeQualifiedName = "org.kotools.types.Zero"
        val invalidValue: Any = Random.nextInt(1..Int.MAX_VALUE)
        val reason = "It should be equal to the zero number."
        val error =
            InstanceCreationError(typeQualifiedName, invalidValue, reason)
        val actual: String = error.toString()
        val expected = "Unable to create an instance of '$typeQualifiedName' " +
                "from '$invalidValue'. $reason"
        assertEquals(expected, actual)
    }
}
