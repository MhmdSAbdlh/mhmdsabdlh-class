# Dock
JAVA DOCK PANEL

ADD A DOCK PANEL TO YOU FRAME

-DOCK:
  1. Dock dock= new Dock(RADUIS_SIZE);
  2. dock.setHyW(30);
  3. dock.setIcon(icon1, icon2, icon3, icon4);
  4. dock.setToolText("text1","text2", "text3", "text4");
     
 * RADUIS SIZE IS AN INT
 * icon1, icon2, icon3, icon4 is the ImageIcon for the 4 buttons
 * THE METHOD setHyW is for set the size of the icon

-BORDER:
1. Color borderColor = Color.black; // BORDER COLOR
2. int borderThickness = 2; //BORDER THICKNESS
3. dock.setBorder(new RoundedCornerBorder(borderColor, borderThickness));

-EVENT:
  1. dock.addActionLis1(EVENT1) // ADD ACTION LISTENER TO THE FIRST ITEM
  2. dock.addActionLis2(EVENT2) // ADD ACTION LISTENER TO THE SECONG ITEM
  3. dock.addActionLis3(EVENT3) // ADD ACTION LISTENER TO THE THIRD ITEM
  4. dock.addActionLis4(EVENT4) // ADD ACTION LISTENER TO THE FOURTH ITEM


-PREVIEW:

![PHOTO1](https://raw.githubusercontent.com/MhmdSAbdlh/Dock/main/preview/1.png)

![PHOTO2](https://raw.githubusercontent.com/MhmdSAbdlh/Dock/main/preview/2.png)
