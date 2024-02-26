package mx.edu.utez.mexprotec.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service

public class Mailer {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    public String mailFrom;
    public boolean EnviarMensajeBienvenida(String correo, String nombre, String asunto) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(correo);
        helper.setFrom(mailFrom);
        helper.setSubject(asunto);
        //Plantilla del correo de bienvenida
        helper.setText("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "  </head>\n" +
                "\n" +
                "  <body style=\"font-family: 'Segoe UI', sans-serif; text-align: center\">\n" +
                "    <div style=\"width: 100%\">\n" +
                "      <div style=\"margin-top: 20px; display: flex; justify-content: center\">\n" +
                "        <div style=\"width: 550px; margin: auto\">\n" +
                "          <div\n" +
                "            style=\"\n" +
                "              border: 2px solid #ccc0c1;\n" +
                "              border-radius: 0.5rem;\n" +
                "              overflow: hidden;\n" +
                "            \"\n" +
                "          >\n" +
                "            <img\n" +
                "              src=\"https://demo.stripocdn.email/content/guids/d0dee27c-b951-4be2-9e65-fe5d431243a4/images/booksg5a3638f0519201633971795.jpg\"\n" +
                "              style=\"width: 100%\"\n" +
                "              alt=\"Libro\"\n" +
                "            />\n" +
                "            <div style=\"background-color: #b53439; height: 35px\"></div>\n" +
                "            <div style=\"padding: 15px\">\n" +
                "              <p style=\"font-weight: bold; font-size: 1.5rem; margin-top: -8px;\">\n" +
                "                ¡Bienvenido "+ nombre +"!\n" +
                "              </p>\n" +
                "              <p>\n" +
                "                Estamos emocionados de tenerte con nosotros, listo para entrar\n" +
                "                en el maravilloso mundo de la lectura. Aquí, cada página es una\n" +
                "                aventura y cada libro es una puerta a un nuevo universo.\n" +
                "                Prepárate para explorar historias fascinantes, aprender de\n" +
                "                grandes mentes y entrar en mundos imaginarios.\n" +
                "              </p>\n" +
                "              <p>\n" +
                "                <small style=\"font-style: italic; color: #6c757d\"\n" +
                "                  >Este mensaje es automático. No es necesario responder.</small\n" +
                "                >\n" +
                "              </p>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n", true);

        //Enviar el correo electrónico
        javaMailSender.send(message);
        return true;
    }
    public boolean EnviarDetalleRenta(String correo, String nombre, String titulo, String autor, String genero, String fecha, String anio) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(correo);
        helper.setFrom(mailFrom);
        helper.setSubject("Detalles de renta");
        //Plantilla del correo de renta
        helper.setText("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "  </head>\n" +
                "  <style>\n" +
                "    td {\n" +
                "      border: 1px solid #ddd;\n" +
                "      padding: 8px;\n" +
                "    }\n" +
                "  </style>\n" +
                "  <body style=\"font-family: 'Segoe UI', sans-serif; text-align: center\">\n" +
                "    <div style=\"width: 100%\">\n" +
                "      <div style=\"margin-top: 20px; display: flex; justify-content: center\">\n" +
                "        <div style=\"width: 550px; margin: auto\">\n" +
                "          <div\n" +
                "            style=\"\n" +
                "              border: 2px solid #ccc0c1;\n" +
                "              border-radius: 0.5rem;\n" +
                "              overflow: hidden;\n" +
                "            \"\n" +
                "          >\n" +
                "            <img\n" +
                "              src=\"https://demo.stripocdn.email/content/guids/d0dee27c-b951-4be2-9e65-fe5d431243a4/images/booksg5a3638f0519201633971795.jpg\"\n" +
                "              style=\"width: 100%\"\n" +
                "              alt=\"Libro\"\n" +
                "            />\n" +
                "            <div style=\"background-color: #b53439; height: 35px\"></div>\n" +
                "            <div style=\"padding: 15px\">\n" +
                "              <p style=\"font-weight: bold; font-size: 1.5rem; margin-top: -8px\">\n" +
                "                Detalles de Renta\n" +
                "              </p>\n" +
                "              <p>Hola "+nombre+", parece que has rentado un libro.</p>\n" +
                "              <div style=\"justify-content: center\">\n" +
                "                <table\n" +
                "                  style=\"\n" +
                "                    width: 100%;\n" +
                "                    border-collapse: collapse;\n" +
                "                    margin-top: 20px;\n" +
                "                  \"\n" +
                "                >\n" +
                "                  <tr>\n" +
                "                    <td></td>\n" +
                "                    <td>Título</td>\n" +
                "                    <td>Autor</td>\n" +
                "                    <td>Género</td>\n" +
                "                    <td>Año</td>\n" +
                "                  </tr>\n" +
                "                  <tr>\n" +
                "                    <td>\n" +
                "                      <img\n" +
                "                        src=\"https://cdn.icon-icons.com/icons2/2640/PNG/512/book_reading_icon_159273.png\"\n" +
                "                        alt=\"\"\n" +
                "                        width=\"50px\"\n" +
                "                        height=\"50px\"\n" +
                "                        style=\"display: block; margin: 0 auto\"\n" +
                "                      />\n" +
                "                    </td>\n" +
                "                    <td>"+titulo+"</td>\n" +
                "                    <td>"+autor+"</td>\n" +
                "                    <td>"+genero+"</td>\n" +
                "                    <td>"+anio+"</td>\n" +
                "                  </tr>\n" +
                "                </table>\n" +
                "              </div>\n" +
                "              <p style=\"font-weight: bold\">\n" +
                "                Tendras disponible el libro hasta el "+fecha+".\n" +
                "              </p>\n" +
                "              <p>\n" +
                "                <small style=\"font-style: italic; color: #6c757d\"\n" +
                "                  >Este mensaje es automático. No es necesario responder.</small\n" +
                "                >\n" +
                "              </p>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n", true);

        //Enviar el correo electrónico
        javaMailSender.send(message);
        return true;
    }

}
