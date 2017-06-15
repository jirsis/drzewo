var debug = require('debug')('drzewo:album:routes');
var express = require('express');
var router = express.Router();
var path = require('path');
var HttpStatus = require('http-status-codes');

var config=require('../config');

router.get('', function(req, res){
    debug('render index');
    res.render('index', {
        'title': {'es': 'mi título'},
        'photoServer': 'http://localhost:8080',
        'url': 'http://localhost:8080/thumbnails/qrqwerqew/',
        'photos': [
            {
                'original': {
                    'image': '/albums/qrqwerqew/_MG_6670.JPG',
                    'width': '5184',
                    'height': '3456'
                },
                'thumbnail': {
                    'image': '/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCABLADIDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwDmLq1vblS5gfYOuBWcbRgM7Tj6V6/a/ZrizKzWwi54GetVdUXTBaLaywqE65UYrKGJS0sOVFvW55Obb2ppt/au6+x6OYnZYyTjgZqjpml2dze5mbFuuS3vW3t42MvZSOQNsScAc1NDo15csVhtZXI6gKa9Mt4dEtSrW9sjc5Vm5NW31WFDvCgEdcDGayliuyNFR7s8ibTLhWKtA4IOCNtFeqtqkLMSYFOTnpRU/WX2H7HzMeLUYbu38y1dmXPUVma3rbiOMb8sg5HtXLWOvjSrUxRDcpJOT61mXuszX04LkKtZqJbZvprpKMqnaCce+KSLUnUny3Kjp9a5COcrOxQ5q7b3LEjcw3DpQ4iTO7029nnAWPywR3JxWh5sofErAEenNcNbatcWhWRCMg5xVm68Z3cq+WkUYc9WA5FTZ9irnbiaHHU0V5qdcviSTO2aKORhzFuXwvLCqM0yMhbGPrWXq2lzaey7iDu4+UV38Onwgc3Mzd9rNTLrTbG7j2SMSvYk9K544lp6mkqaa0POFgZcFSMEcn39KtrCu6NFB3H2rrG8NaeNvLkY6A96oaVZodVuELFlhyFyOvNbe3TTa6EezaM/7G6ShOpAGfarM/hyYW/mw7d5GSK6WO2Rp5NyqRjjIqXakcIRU3MOuDWDry6FqmupwP2GUfeQg9xiiuve1kaRmBUAkkCitPrAvZkZu7sOF+zvgdGzxVmK5uUj+aBCQemaiFzKHBjYbSOR0H0xT/tMp48wJ6kCsGvINSVr4Ku9rNs/7IrD0z7TbanJd+SxWXJZSK2RcXbBt0/yr904FJLdvJGAzLjH0OaIuyatuMmF+HAaSEr796BNby5O7YR2BrPVW2nbOzc8ioZY8Nk5PoBS5V0DmZcaK2LE/aH60VR8tv7tFVZ9wv5GWdXZA3C4HfPWoF1i6LhlBIPWsyLh8jtSu7C4fBI5rs9nHsY8zNF9ZnHGCM9QT1pV1SZjnGGArI3FssTk561MhJUgn+Gn7OKFdmtHqjk7jxxjirH9oRyDcZdrAdKxFH7pW7kdavyopt87RnAqHCNxpsn/ALQh7yN+dFVmADkADAPpRS5EO7P/2Q=='
                }
            }
        ]
    });
});

module.exports = router;
