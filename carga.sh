#!/bin/bash

# Credenciais
CREDENTIALS=$(echo -n "guest:guest" | base64)

# Função para gerar string aleatória
generate_random_string() {
    cat /dev/urandom | tr -dc 'A-Z0-9' | fold -w $1 | head -n 1
}

# Função para enviar mensagem
send_to_rabbitmq() {
    local message=$1
    curl -v -H "Authorization: Basic $CREDENTIALS" \
         -H "Content-Type: application/json" \
         -X POST \
         http://localhost:15672/api/exchanges/%2F/order-exchange/publish \
         -d '{
               "properties": {},
               "routing_key": "order.key",
               "payload": '"$message"',
               "payload_encoding": "string"
             }'
}

# Número de pedidos a serem gerados
NUM_ORDERS=200000000

# Loop para gerar e enviar pedidos
for ((i=1; i<=$NUM_ORDERS; i++)); do
    # Gerar códigos únicos
    order_code1="ORD$(generate_random_string 6)"
    order_code2="ORD$(generate_random_string 6)"
    tracking1="TRACK$(generate_random_string 9)"
    tracking2="TRACK$(generate_random_string 9)"

    # Criar mensagem JSON com escape apropriado
    message='"[{\"code\":\"'$order_code1'\",\"tracking\":\"'$tracking1'\",\"dateReceive\":\"2025-02-15T10:30:00\",\"items\":[{\"code\":\"P123\",\"quantity\":2,\"price\":50.0},{\"code\":\"P12321\",\"quantity\":2,\"price\":50.0}]},{\"code\":\"'$order_code2'\",\"tracking\":\"'$tracking2'\",\"dateReceive\":\"2025-02-16T12:00:00\",\"items\":[{\"code\":\"P789\",\"quantity\":1,\"price\":30.0}]}]"'

    # Enviar para o RabbitMQ e mostrar resposta completa
    response=$(send_to_rabbitmq "$message")
    echo "Response: $response"

    echo "Enviado lote $i de $NUM_ORDERS"

    # Pequena pausa para não sobrecarregar
    sleep 0.1
done

echo "Concluído! $NUM_ORDERS lotes de pedidos foram enviados."