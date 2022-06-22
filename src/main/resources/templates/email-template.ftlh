<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Sending Email with Freemarker HTML Template Example</title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>

    <!-- use the font -->
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            font-size: 48px;
            
        }
    </style>
</head>
<body style="margin: 0; padding: 0;">

    <table align="left" border="0" cellpadding="0" cellspacing="0" width="600" style="border-collapse: collapse;">
        <tr>
            <td align="center" bgcolor="#000000" style="padding: 40px 0 30px 0;">
                <img src="https://github.com/ShivamVerma380/JwtRefreshToken/blob/master/OTP.png?raw=true"  style="width:600px ;height:300px"/>
            </td>
        </tr>
        <tr>
            <td bgcolor="#FF6262"  style="padding: 40px 30px 40px 30px;">
                <p bgcolor="#FFFFFF">Dear ${name},</p>
                <p bgcolor="#FFFFFF"> Here is your OTP: <b>${otp}</b></p>
                <p bgcolor="#FFFFFF">Thanks and keep shopping with Mahavir Electronics</p>
            </td>
        </tr>
    </table>

</body>
</html>