using Microsoft.AspNetCore.Mvc;

namespace AdApp.Controllers
{
    using AdApp.Rabbit;
    using Microsoft.AspNetCore.Mvc;
    using System.Threading.Tasks;

    namespace YourNamespace.Controllers
    {
        [Route("[controller]")]
        [ApiController]
        public class AdsController : ControllerBase
        {
            private readonly RabbitMqPublisherService _rabbitMqPublisherService;

            public AdsController(RabbitMqPublisherService rabbitMqPublisherService)
            {
                _rabbitMqPublisherService = rabbitMqPublisherService;
            }

            [HttpPost("send-ad")]
            public async Task<IActionResult> SendAdAsync([FromQuery] string adContent)
            {
       
                await _rabbitMqPublisherService.PublishMessage(adContent);
                return Ok(new { message = "Ad sent to RabbitMQ" });
            }
        }
    }

}
