using AdApp.Rabbit;
using RabbitMQ.Client;
using Steeltoe.Discovery.Client;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.WebHost.UseUrls("http://0.0.0.0:5186");


builder.Services.AddRazorPages();
builder.Services.AddControllers();

builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
builder.Services.AddDiscoveryClient(builder.Configuration);

builder.Services.AddSingleton<IConnectionFactory>(serviceProvider =>
{
    var configuration = builder.Configuration;
    var host = configuration["RMQ:Host"]; 
    var port = configuration["RMQ:Port"]; 
    var username = configuration["RMQ:Username"]; 
    var password = configuration["RMQ:Password"];  

    // For debugging purposes, log the configuration values
    Console.WriteLine($"RabbitMQ Host: {host}");
    Console.WriteLine($"RabbitMQ Port: {port}");
    Console.WriteLine($"RabbitMQ Username: {username}");

    return new ConnectionFactory
    {
        HostName = host,
        Port = int.Parse(port),  // Make sure to parse port to int
        UserName = username,
        Password = password
    };
});

builder.Services.AddSingleton<RabbitMqPublisherService>();
var app = builder.Build();


// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}





app.UseAuthorization();
app.MapRazorPages();
app.MapControllers();

app.Run();
