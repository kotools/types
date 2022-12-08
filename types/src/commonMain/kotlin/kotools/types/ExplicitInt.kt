package kotools.types

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.shared.Project.Types
import kotools.shared.SinceKotools

/** Representation of explicit integers. */
@SinceKotools(Types, "3.2")
public sealed interface ExplicitInt {
    /** Returns this value as an [Int]. */
    public fun toInt(): Int

    /** Returns this value as a [String]. */
    override fun toString(): String
}

internal sealed class ExplicitIntSerializer<A : ExplicitInt>(
    private val builder: (Int) -> Result<A>
) : KSerializer<A> {
    private val delegate: KSerializer<Int> = Int.serializer()
    override val descriptor: SerialDescriptor = delegate.descriptor

    override fun serialize(encoder: Encoder, value: A) {
        val intValue: Int = value.toInt()
        delegate.serialize(encoder, intValue)
    }

    override fun deserialize(decoder: Decoder): A {
        val value: Int = delegate.deserialize(decoder)
        return builder(value)
            .getOrThrow()
    }
}
