УДАЛИТЬ ТАБЛИЦУ, ЕСЛИ СУЩЕСТВУЮТ пользователи;
СОЗДАТЬ ТАБЛИЦУ пользователей
(
    id СИМВОЛ (36) ПЕРВИЧНЫЙ КЛЮЧ,
    войти в систему VARCHAR (255) UNIQUE NOT NULL,
    пароль VARCHAR (255) NOT NULL,
    status VARCHAR (255) NOT NULL ПО УМОЛЧАНИЮ 'active'
);

УДАЛИТЬ ТАБЛИЦУ, ЕСЛИ ЕСТЬ карты;
СОЗДАТЬ ТАБЛИЦЫ карточки
(
    id СИМВОЛ (36) ПЕРВИЧНЫЙ КЛЮЧ,
    user_id CHAR (36) НЕ NULL,
    число VARCHAR (19) UNIQUE NOT NULL,
    balance_in_kopecks INT НЕ NULL,
    ИНОСТРАННЫЙ КЛЮЧ (user_id) ССЫЛКИ на пользователей (id)
);

УДАЛИТЬ ТАБЛИЦУ, ЕСЛИ СУЩЕСТВУЕТ auth_codes;
СОЗДАТЬ ТАБЛИЦУ auth_codes
(
    id СИМВОЛ (36) ПЕРВИЧНЫЙ КЛЮЧ,
    user_id CHAR (36) НЕ NULL,
    код VARCHAR (6) NOT NULL,
    создан TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ИНОСТРАННЫЙ КЛЮЧ (user_id) ССЫЛКИ на пользователей (id)
);

УДАЛИТЬ ТАБЛИЦУ, ЕСЛИ СУЩЕСТВУЕТ card_transactions;
СОЗДАТЬ ТАБЛИЦУ card_transactions
(
    id СИМВОЛ (36) ПЕРВИЧНЫЙ КЛЮЧ,
    источник VARCHAR (19) NOT NULL,
    целевой VARCHAR (19) NOT NULL,
    amount_in_kopecks INT NOT NULL,
    создано TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
