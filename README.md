# BECA Takımı

BECA ekibi olarak amacımız, NLP projeleri için gerekli veri setlerini hem interaktif bir şekilde kullanıcılardan toplayan, hem de daha sonrasında doğal dil işleme ile bu verileri eşsiz hikayeler yazmak için kullanan Baazi uygulamasını ülkemize kazandırmaktır.

# Baazi

Doğal dil işleme bir yapay zeka alt dalıdır ve içinde bir çok dil işleme tekniği bulundurur. Baazi projesindeki asıl amaç, doğal dil işleme için proje geliştiricilerine zenginleştirilmiş veri seti olanağı sağlamaktır.

BECA takımı ve Baazi uygulaması hakkında daha bilgi almak için, [tıklayın](https://drive.google.com/drive/folders/1AaLwCFqb7YzworkOYEgerkHn7mFM6B6s?usp=sharing).

## Başlangıç

BECA API, Java programlama dilinde Spring boot ve MongoDB kullanılarak yazılmıştır.

## Gereksinimler

API'ı çalıştırabilmek için Java'nın cihazınıza kurulu olduğundan emin olun.

API'ın database'e bağlanabilmesi için ```BECA_MONGO_URL``` çevre değişkenini (environment variable) sisteminize eklemeniz gerekmektedir.

API'ın geçerli JWT anahtarları üretebilmesi için bir gizli anahtar (secret key) belirlemeniz ve bunu ```BECA_JWT_KEY``` çevre değişkeni (environment variable) olarak sisteminize eklemeniz gerekmektedir.

#### BECA API'ı dokku veya benzeri (heroku) sistemler üzerine çalıştırmanız ve database olarak Atlas MongoDB veya benzeri sistemler kullanmanız önerilir.

## Kurulum

BECA API'ı heroku üzerinde kurulum işlemini gerçekleştirmek gerçekleştirmek çok kolay. İlk yapmanız gereken bir heroku hesabı oluşturmanız ve sisteminize Heroku CLI'ı kurmanız.

Ardından cihazınızdan kullanıcı girişi yapmak için ```heroku login``` komutu vermeniz yeterli olucaktır.

Bunun ardından ```git``` için gerekli komutları girmeniz gerekmektedir.

```
git init
git add .
git commit -m "first commit"
```

VSC için gerekli komutları verdikden sonra Heroku ayarlarını yapmaya geçebilirsiniz.

```
heroku create <app-name>
heroku config:set BECA_MONGO_URL="<AtlasMongoDBURL>"
heroku config:set BECA_JWT_KEY="<AppJWTSecretKey>"
heroku git:remote -a <app-name>
```

Heroku için gerekli ayarları yaptıkdan sonra ```git push heroku master``` komutunu girmeniz yeterli olucaktır.

API'ı kullanabilmek için gerekli tüm endpoint'ler ve açıklamalarına [buradan](https://documenter.getpostman.com/view/2246006/T1DjjzBy) ulaşabilirsiniz.

## Lisans
[MIT License](LICENSE)
