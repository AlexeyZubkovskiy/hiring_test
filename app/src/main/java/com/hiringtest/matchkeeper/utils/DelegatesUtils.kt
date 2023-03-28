package com.hiringtest.matchkeeper.utils

//Used for cases when we don't need thread safety. Reduces overhead.
fun <T> lazyUnsafe(initializer: () -> T): Lazy<T> =
	lazy(mode = LazyThreadSafetyMode.NONE, initializer = initializer)