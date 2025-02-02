# Dock
JAVA CLASSES


**AUTOCOMPLETE:** 
  - AUTOCOMPLETE A TEXTFIELD USING KEYWORDS
  - CALLED: public AutoComplete(JTextField textField, List<String> keywords)
    
![alt text](https://github.com/MhmdSAbdlh/mhmdsabdlh-class/blob/main/preview/autocomplete.png)


**CALCULATOR:**
  - SOME SIMPLE CALCULATION LIKE +,-,*,/ AND A COPY BUTTON
  - AUTOCOPY THE NON-ZERO VALUE WHEN EXIT THE PANEL
  - 'ESC' BUTTON TO CLOSE THE PANEL
  - CALLED: public CalculatorPanel(JFrame parentFrame)

![alt text](https://github.com/MhmdSAbdlh/mhmdsabdlh-class/blob/main/preview/calculator.png)


**DOCK:**
  - DOCK PANEL OF 4 ELEMENTS
  - AUTOCHANGE THE OPACITY WHEN THE MOUSE HOUVER IN/OUT EACH BUTTON
  - AUTORESIZE THE ICONS ACCORDING TO THE DOCK HEIGHT
  - CALLED: public Dock(int cornerRadius)
  - USED METHOD: 
    1. public void setMouseListener() // TO ADD THE OPACITY ANIMATION
    2. public void setIcon(ImageIcon icon, ImageIcon icon2, ImageIcon icon3, ImageIcon icon4) // TO SET THE ICONS OF THE BUTTONS
    3. public void setToolText(String text1, String text2, String text3, String text4) // SET TOOLTIP FOR EACH BUTTONS
    4. public void setHyW(int height) // CHANGE THE HEIGHT TO THE DOCK
    5. public void addActionLis1(ActionListener al) // ADD ACTIONLOSTENER FOR EVERY BUTTON
    6. public void setDockColor(Color newColor) // SET DOCK COLOR

![alt text](https://github.com/MhmdSAbdlh/mhmdsabdlh-class/blob/main/preview/dock.png)


**ENCRYPTION:**
 - ENCRYPT ANY TEXT BY CHANGING THE ORDER OF THE LETTERS
 - CALLED:
   * public String encrypt(String finalText) OR public String encrypt(int number) // FOR ENCRYPTION
   * public String decrypt(String finalText) OR public String decrypt(int number) // FOR DECRYPTION


**HALFCIRLE:**
  - A LABEL THAT HAS THE STYLE OF HALF CIRCLE WITH ANY WIDTH/HEIGHT YOU WANT
  - CALLED:
    * public HalfCircle(String text)
    * public void setCircleColor(Color newColor) // TO SET THE CIRCLE COLOR
	  
![alt text](https://github.com/MhmdSAbdlh/mhmdsabdlh-class/blob/main/preview/halfcirle.png)


**ICONWITHTEXT:**
  - A CLASS TO ADD TEXT INSIDE AN IMAGEICON
  - CALLED:
    * public IconWithText(ImageIcon imageIcon, String text, Color textColor, Position position, Font font)
    * public IconWithText(ImageIcon imageIcon, String text, Color textColor, Position position)
    * public IconWithText(ImageIcon imageIcon, String text, Color textColor)
 - POSITION: TOP, BOTTOM, LEFT, RIGHT, CENTER, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT

![alt text](https://github.com/MhmdSAbdlh/mhmdsabdlh-class/blob/main/preview/iconwithtext.png)


**IMAGEBLUR:**
  - BLUR ANY IMAGE DIRECTLY
  - CALLED:
    * public BufferedImage blurImageFromResource(String resourcePath) // FROM INSIDE THE APP
    * public BufferedImage blurImageFromFile(String imagePath) // ANY OUTSIDE LOCATION
    * public static BufferedImage blurImage(BufferedImage image) // BUFFEREDIMAGE

![alt text](https://github.com/MhmdSAbdlh/mhmdsabdlh-class/blob/main/preview/imageblur.png)


**IMAGEEFFECT:**
 - CLASS TO CHANGE OR ADD EFFECT TO IMAGE
 - public static ImageIcon convertIconToImageIcon(Icon icon) // CONVERT ICON TO IMAGEICON
 - public static ImageIcon changeOpacity(Image srcImage, float opacity) // CHANGE OPCAITY TO A GIVEN IMAGE (0F TO 1.0F)
 - public static ImageIcon getScaledImage(Image srcImg, int w, int h) // RESCALE A GIVEN IMAGE TO ANY WIDTH,HEIGHT YOU WANT
 - static public ImageIcon invertColor(ImageIcon originalIcon) // INVERT THE COLOR OF A GIVEN IMAGE(BLACK -> WHITE)
 - public static ImageIcon createImageIconFromText(String text, int width, int height, Color textColor, Color backgroundColor, Font font) // FOR A GIVEN TEXT CREATE AN IMAGE

![alt text](https://github.com/MhmdSAbdlh/mhmdsabdlh-class/blob/main/preview/invertcolor.png)
![alt text](https://github.com/MhmdSAbdlh/mhmdsabdlh-class/blob/main/preview/opacity.png)
![alt text](https://github.com/MhmdSAbdlh/mhmdsabdlh-class/blob/main/preview/texttoimage.png)


**MODERNDIALOG:**
  - A MODERN DIALOG WITH ROUNDER CORNERS, AND LIGHT DESIGN CONTAIN ICON AT TOP, MESSAGE AT MIDDLE AND BUTTONS AT SOUTH
  - CALLED: public ModernDialog(JFrame parent, String closeMessage, IconType iconType)
  - ICONTYPE: WARNING, ERROR, INFO, QUESTION
  - public void setBorderColor(Color newColor) // TO SET BORDER COLOR
  - public void setColor(Color bgColor) // TO SET BACKGROUND COLOR
  - public void setTextColor(Color textC) // TO SET FOREGROUND COLOR
  - public void addMainButton(String text, Color color, Runnable action) // ADD MAIN BUTTON LIKE YES, NO
  - public void addExtraButton(String text, Color color, Runnable action) // ADD EXTRA BUTTON LIKE CANCEL, LATER, ....

![alt text](https://github.com/MhmdSAbdlh/mhmdsabdlh-class/blob/main/preview/moderndialog1.png)
![alt text](https://github.com/MhmdSAbdlh/mhmdsabdlh-class/blob/main/preview/moderndialog2.png)

**PasswordDialog:**
 - Dialog contain lock icon, and message, password with hide/show icon and tryleft or subtitle and ok cancel button
 - public PasswordDialog(JFrame parent) //initialiize
 - public void showMessage(String closeMessage, String tLeft) // show the dialog with the message and the subtitle
 - public void setBorderColor(Color newColor) // set dialog border color
 - public void setColor(Color bgColor) // set dialog panel color
 - public void setTextColor(Color textC) // set message and submessage color
 - public void setPassword(String password) // set the choosen password
 - public String getPassword() // get the user input password
 - public MessageType getMessageType() // get the button pressed: ok or cancel
 - public void autoUnlock() // auto unlock the dialog without hitting a password(I used it in some cases)

![alt text](https://github.com/MhmdSAbdlh/mhmdsabdlh-class/blob/main/preview/passworddialog.png)


**PasswordField:**
 - Modern password field with round border
 - It has by default a hide/show icon to the password
 - public PasswordField() // to call the password
 - public void setRound(int round) // to set the round

![alt text](https://github.com/MhmdSAbdlh/mhmdsabdlh-class/blob/main/preview/passwordfield.png)


**ROUNDBUTTON:**
  - BUTTON WITH ROUND CORNERS
  - CALLED: public RoundButton(String text, int radius)
  - public void setFillColor(Color fillColor) // BACKGROUND COLOR
  - public void setBorderColor(Color borderC) // BORDER COLOR

![alt text](https://github.com/MhmdSAbdlh/mhmdsabdlh-class/blob/main/preview/roundbutton.png)


**ROUNDLABEL:**
  - LABEL WITH ROUND CORNERS
  - CALLED: public RoundLabel(String text, int borderRadius)
  - public void setFillColor(Color fillColor) // SET THE BACKGROUND COLOR


**TEXTEFFECT:**
  - SCRAMBELTEXT: TO SCRAMBLE CERTAIN WORD, CALLED:public static String scrambleText(String text)
  - GETFIRSTTHREELETTERS: TO GET THE FIRST 3 LETTERS FOR A GIVEN WORD, CALLED: public static String getFirstThreeLetters(String str)
  
![alt text](https://github.com/MhmdSAbdlh/mhmdsabdlh-class/blob/main/preview/scramble.png)



**MOHAMAD ABDALLAH ABBASS ©**
