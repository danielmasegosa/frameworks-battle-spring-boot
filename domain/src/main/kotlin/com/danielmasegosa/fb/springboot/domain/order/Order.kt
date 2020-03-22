package com.danielmasegosa.fb.springboot.domain.order

data class Order (val identifier: String, val items: List<Item>, val address: Address)