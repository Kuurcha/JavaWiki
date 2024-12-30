

using RabbitMQ.Client;
using System;
using System.Text;


namespace AdApp.Rabbit
{

    public class RabbitMqPublisherService
    {
        private readonly IConnection _connection;
        private readonly IChannel _channel;

        public RabbitMqPublisherService(IConnectionFactory connectionFactory)
        {
            _connection = connectionFactory.CreateConnectionAsync().Result;
            _channel = _connection.CreateChannelAsync().Result;
            _channel.QueueDeclareAsync(queue: "adQueue", durable: false, exclusive: false, autoDelete: false, arguments: null).Wait();
        }

        public async Task PublishMessage(string message)
        {
            var body = Encoding.UTF8.GetBytes(message);

            var basicProperties = new BasicProperties();

            basicProperties.ContentType = "text/plain";
            basicProperties.DeliveryMode = DeliveryModes.Persistent;

            await _channel.BasicPublishAsync(
                    exchange: "",           
                    routingKey: "adQueue",   
                    mandatory: false,        
                    basicProperties: basicProperties,
                    body: body
                );
            Console.WriteLine($" [x] Sent {message}");
        }

        public async void Close()
        {
           await _channel.CloseAsync();
           await _connection.CloseAsync();
        }
    }
}
