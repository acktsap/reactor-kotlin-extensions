/*
 * Copyright (c) 2011-2021 VMware Inc. or its affiliates, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package reactor.kotlin.core.publisher

import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import java.util.stream.Stream
import kotlin.reflect.KClass


/**
 * Extension to convert any [Publisher] of [T] to a [Flux].
 *
 * Note this extension doesn't make much sense on a [Flux] but it won't be converted so it
 * doesn't hurt.
 *
 * @author Simon Baslé
 * @since 3.1.1
 */
fun <T : Any> Publisher<T>.toFlux(): Flux<T> = Flux.from(this)

/**
 * Extension for transforming an [Iterator] to a [Flux].
 *
 * @author Sebastien Deleuze
 * @since 3.1
 */
fun <T : Any> Iterator<T>.toFlux(): Flux<T> = toIterable().toFlux()

/**
 * Extension for transforming an [Iterable] to a [Flux].
 *
 * @author Sebastien Deleuze
 * @since 3.1
 */
fun <T : Any> Iterable<T>.toFlux(): Flux<T> = Flux.fromIterable(this)

/**
 * Extension for transforming a [Sequence] to a [Flux].
 *
 * @author Sebastien Deleuze
 * @since 3.1
 */
fun <T : Any> Sequence<T>.toFlux(): Flux<T> = Flux.fromIterable(object : Iterable<T> {
    override fun iterator(): Iterator<T> = this@toFlux.iterator()
})

/**
 * Extension for transforming a [Stream] to a [Flux].
 *
 * @author Sebastien Deleuze
 * @since 3.1
 */
fun <T : Any> Stream<T>.toFlux(): Flux<T> = Flux.fromStream(this)

/**
 * Extension for transforming a [BooleanArray] to a [Flux].
 *
 * @author Sebastien Deleuze
 * @since 3.1
 */
fun BooleanArray.toFlux(): Flux<Boolean> = this.toList().toFlux()

/**
 * Extension for transforming a [ByteArray] to a [Flux].
 *
 * @author Sebastien Deleuze
 * @since 3.1
 */
fun ByteArray.toFlux(): Flux<Byte> = this.toList().toFlux()

/**
 * Extension for transforming a [ShortArray] to a [Flux].
 *
 * @author Sebastien Deleuze
 * @since 3.1
 */
fun ShortArray.toFlux(): Flux<Short> = this.toList().toFlux()

/**
 * Extension for transforming a [IntArray] to a [Flux].
 *
 * @author Sebastien Deleuze
 * @since 3.1
 */
fun IntArray.toFlux(): Flux<Int> = this.toList().toFlux()

/**
 * Extension for transforming a [LongArray] to a [Flux].
 *
 * @author Sebastien Deleuze
 * @since 3.1
 */
fun LongArray.toFlux(): Flux<Long> = this.toList().toFlux()

/**
 * Extension for transforming a [FloatArray] to a [Flux].
 *
 * @author Sebastien Deleuze
 * @since 3.1
 */
fun FloatArray.toFlux(): Flux<Float> = this.toList().toFlux()

/**
 * Extension for transforming a [DoubleArray] to a [Flux].
 *
 * @author Sebastien Deleuze
 * @since 3.1
 */
fun DoubleArray.toFlux(): Flux<Double> = this.toList().toFlux()

/**
 * Extension for transforming an [Array] to a [Flux].
 *
 * @author Sebastien Deleuze
 * @since 3.1
 */
fun <T> Array<out T>.toFlux(): Flux<T> = Flux.fromArray(this)

private fun <T> Iterator<T>.toIterable() = object : Iterable<T> {
    override fun iterator(): Iterator<T> = this@toIterable
}

/**
 * Extension for transforming an exception to a [Flux] that completes with the specified error.
 *
 * @author Sebastien Deleuze
 * @since 3.1
 */
fun <T> Throwable.toFlux(): Flux<T> = Flux.error(this)

/**
 * Extension for [Flux.cast] providing a `cast<Foo>()` variant.
 *
 * @author Sebastien Deleuze
 * @since 3.1
 */
inline fun <reified T : Any> Flux<*>.cast(): Flux<T> = cast(T::class.java)


/**
 * Extension for [Flux.doOnError] providing a [KClass] based variant.
 *
 * @author Sebastien Deleuze
 * @since 3.1
 */
fun <T, E : Throwable> Flux<T>.doOnError(exceptionType: KClass<E>, onError: (E) -> Unit): Flux<T> =
        doOnError(exceptionType.java) { onError(it) }

/**
 * Extension for [Flux.onErrorMap] providing a [KClass] based variant.
 *
 * @author Sebastien Deleuze
 * @since 3.1
 */
fun <T, E : Throwable> Flux<T>.onErrorMap(exceptionType: KClass<E>, mapper: (E) -> Throwable): Flux<T> =
        onErrorMap(exceptionType.java) { mapper(it) }

/**
 * Extension for [Flux.ofType] providing a `ofType<Foo>()` variant.
 *
 * @author Sebastien Deleuze
 * @since 3.1
 */
inline fun <reified T : Any> Flux<*>.ofType(): Flux<T> = ofType(T::class.java)

/**
 * Extension for [Flux.onErrorResume] providing a [KClass] based variant.
 *
 * @author Sebastien Deleuze
 * @since 3.1
 */
fun <T : Any, E : Throwable> Flux<T>.onErrorResume(exceptionType: KClass<E>, fallback: (E) -> Publisher<T>): Flux<T> =
        onErrorResume(exceptionType.java) { fallback(it) }

/**
 * Extension for [Flux.onErrorReturn] providing a [KClass] based variant.
 *
 * @author Sebastien Deleuze
 * @since 3.1
 */
fun <T : Any, E : Throwable> Flux<T>.onErrorReturn(exceptionType: KClass<E>, value: T): Flux<T> =
        onErrorReturn(exceptionType.java, value)

/**
 * Extension for flattening [Flux] of [Iterable]
 *
 * @author Igor Perikov
 * @since 3.1
 */
fun <T : Any> Flux<out Iterable<T>>.split(): Flux<T> = this.flatMapIterable { it }

/**
 * Extension for [Flux.switchIfEmpty] accepting a function providing a Publisher. This allows having a deferred execution with
 * the [switchIfEmpty] operator
 *
 * @author Kevin Davin
 * @author Pavel Grigorenko
 * @since 1.1.3
 */
fun <T> Flux<T>.switchIfEmptyDeferred(s: () -> Publisher<T>): Flux<T> = this.switchIfEmpty(Flux.defer { s() })
