package com.danielmasegosa.fb.springboot.integration

import com.danielmasegosa.fb.springboot.domain.order.Address
import com.danielmasegosa.fb.springboot.domain.order.Item
import com.danielmasegosa.fb.springboot.domain.order.Order
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.hamcrest.Matchers.`is` as Is

@SpringBootTest
@AutoConfigureMockMvc
class CreateOrderRestIT {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    internal fun `should create an order when the create order endpoint is requested`() {
        //given
        val identifier = "order001"
        val item1 = Item("001", "M")
        val item2 = Item("002", "XL")
        val items = listOf(item1, item2)
        val address = Address("Avenida", "Kansas City")

        //when
        var resultActions = mockMvc.perform(post("/api/v1/order").content(asJson(Order(identifier, items, address))).contentType(APPLICATION_JSON))

        //then
        resultActions.andExpect(status().isCreated).andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.identifier", Is(identifier)))
                .andExpect(jsonPath("$.items[0]", Is(item1)))
                .andExpect(jsonPath("$.items[1]", Is(item2)))
                .andExpect(jsonPath("$.address", Is(address)))
    }

    internal fun asJson(order: Order) = ObjectMapper().writeValueAsString(order)

}