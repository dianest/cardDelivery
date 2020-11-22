package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    @BeforeAll
    static void setup() {
        Configuration.timeout = 15000;
    }

    @AfterAll
    static void tearDown() {
        Configuration.timeout = 4000;
    }

    @Test
    public void happyPath() {
        open("http://localhost:9000/");
        SelenideElement form = $(By.tagName("FORM"));
        form.$("[data-test-id=city] input").setValue("Санкт-Петербург");
        for(int i = 0; i < 10; i++) {
            form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        }

        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        form.$("[data-test-id=date] input").setValue(date);
        form.$("[data-test-id=name] input").setValue("Иванов Иван Иванович");
        form.$("[data-test-id=phone] input").setValue("+79876543210");
        form.$("[data-test-id=agreement]").click();
        form.$("[class=button__content]").click();

        SelenideElement notification = $("[data-test-id=notification]");
        notification.shouldBe(
                Condition.visible,
                Condition.text("Встреча успешно забронирована на " + date
                )
        );
    }
}
