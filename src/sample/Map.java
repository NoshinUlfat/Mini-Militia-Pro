package sample;

public class Map {
    int choice;

    public void isChoice(int c) {
        choice=c;
    }

    public boolean isObstacle(double x, double y)
    {
        boolean isObstacle=false;


        if(choice==1) {
            // if(x>350&&x<496&&y<400&&y>326) isObstacle=true;
            if (x > 0 && x < 266 && y < 616 && y > 552) isObstacle = true;
            else if (x > 254 && x < 314 && y < 717 && y > 557) isObstacle = true;
            else if (x > 255 && x < 655 && y < 760 && y > 719) isObstacle = true;
            else if (x > 596 && x < 674 && y < 725 && y > 613) isObstacle = true;
            else if (x > 666 && x < 973 && y < 661 && y > 611) isObstacle = true;
            else if (x > 250 && x < 312 && y < 273 && y > 0) isObstacle = true;
            else if (x > 250 && x < 531 && y < 324 && y > 283) isObstacle = true;
            else if (x > 734 && x < 1068 && y < 340 && y > 292) isObstacle = true;
            else if (x > 1052 && x < 1123 && y < 447 && y > 290) isObstacle = true;
            else if (x > 1054 && x < 1450 && y < 500 && y > 446) isObstacle = true;
            else if (x > 1143 && x < 1493 && y < 785 && y > 743) isObstacle = true;
            else if (x > 1430 && x < 1510 && y < 752 && y > 300) isObstacle = true;
            else if (x > 1360 && x < 1710 && y < 305 && y > 256) isObstacle = true;
            else if (x > 1710 && x < 2000 && y < 650 && y > 600) isObstacle = true;
        }
        else if(choice==2){
            if (x > 0 && x < 290 && y < 580 && y > 480) isObstacle = true;
            else if (x > 280 && x < 650 && y < 661 && y > 545) isObstacle = true;
            else if (x > 875 && x < 1230 && y < 740 && y > 630) isObstacle = true;
            else if (x > 1224 && x < 1585 && y < 660 && y > 550) isObstacle = true;
            else if (x > 1750 && x < 200 && y < 386 && y > 294) isObstacle = true;
            else if (x > 785 && x < 1280 && y < 300 && y > 205) isObstacle = true;
        }

        else{

        }
        return isObstacle;
    }
    public boolean isFire(double x,double y)
    {
        boolean isFire=false;

        if(x>10+0+10&&x<(-10)+266-10&&y<10+616&&y>50+552) isFire=true;
        else if(x>10+254&&x<(-10)+314&&y<10+717&&y>50+557) isFire=true;
        else if(x>10+255&&x<(-10)+655&&y<10+760&&y>50+719) isFire=true;
        else if(x>10+596&&x<(-10)+674&&y<10+725&&y>50+613) isFire=true;
        else if(x>10+666&&x<(-10)+973&&y<10+661&&y>50+611) isFire=true;
        else if(x>10+250&&x<(-10)+312&&y<10+273&&y>50+0) isFire=true;
        else if(x>10+250&&x<(-10)+531&&y<10+324&&y>50+283) isFire=true;
        else if(x>10+734&&x<(-10)+1068&&y<10+340&&y>50+292) isFire=true;
        else if(x>10+1052&&x<(-10)+1123&&y<10+447&&y>50+290) isFire=true;
        else if(x>10+1054&&x<(-10)+1450&&y<10+500&&y>50+446) isFire=true;
        else if(x>10+1143&&x<(-10)+1493&&y<10+785&&y>50+743) isFire=true;
        else if(x>10+1430&&x<(-10)+1510&&y<10+752&&y>50+300) isFire=true;
        else if(x>10+1360&&x<(-10)+1710&&y<10+305&&y>50+256) isFire=true;
        else if(x>10+1710&&x<(-10)+2000&&y<10+650&&y>50+600) isFire=true;

        return isFire;
    }
}
