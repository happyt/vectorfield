/*----------------------------------

Based on https://www.openprocessing.org/sketch/146879

Adding circles, colour, perlin noise


 
int padding;

ArrayList <Mover> m;

int xRes = 50;
int yRes = 28;
float posX;
float posY;
int changer = 0;

  float hue = 200;

boolean bDisplayVectorField = false, displayParticle = true;

void setup ()
{
  size (800, 800);
  //size (720, 405);
  // size (1920, 1080);
  noSmooth();
  //smooth();
  frameRate (30);
  
  colorMode(HSB, 360);

  padding = -2;
  posX = width/2+1;
  posY = 0;
  m = new ArrayList();

  background (#111111);

  for (int i = 0; i < 500; i++)
  {
    m.add (new Mover());
  }
}

void draw ()
{
  randomSeed (0);

 // blur(1);
 // fastblur (1);

  noStroke();
  fill (7, 9);
  rect (0, 0, width, height);

  posX = lerp (posX, (mousePressed ? mouseX : noise (frameCount /500.0) * width /*noise (20+frameCount / 100.0)*1.5*/), 0.05);
  posY = lerp (posY, mousePressed ? map (mouseY, 0, height, 0, 1) : (noise (100+frameCount / 400.0)), 0.05) ;


  if (displayParticle)
  {
    stroke(240);
    for (int i = 0; i < m.size(); i++)
    {
      m.get(i).move();
      m.get (i).setVelo (posX, posY, noise (frameCount / 300.0));
      m.get(i).checkEdges();  
      m.get(i).display();
    }
  }
  //copy (1, 1, width, height, -1, -1, width+3, height+3);
  

  if (bDisplayVectorField) displayVectorField();
}

class Mover 
{
  PVector location;
  PVector velo;
  float speed;
  color colour;

  Mover ()
  {
    location = new PVector(random(width), random(height));
    velo = new PVector(random(-1, 1), random(-1, 1));
    velo.normalize();

    colour = getNextColour();
    speed = random (1, 3);
  }

  void setVelo (float posX, float posY, float stength)
  {

    float angle, fAngle;

    float dis;
    float mm = map (posX, 0, width, -1.2, 1.2);
    if (mm == 0) mm = 0.0001;
    float maxDis = mm*dist (0, 0, width/2, height/2);


    dis = dist (location.x, location.y, width/2, height/2);
    fAngle = map (dis, 0, maxDis, posY, 0);
    angle = map (dis, 0, maxDis, PI, 0) +  random (-PI/4*fAngle, PI/4*fAngle);

    velo.x += cos (angle)*stength;
    velo.y += sin (angle)*stength;
    
    velo.normalize();
  }


  void move ()
  {

    location.add (multi (velo, speed));
  }

  void checkEdges ()
  {
    if (location.x < 0 || location.x > width || location.y < 0 || location.y > height)
    {
      location.x = random (width);
      location.y = random (height);
      colour = getNextColour();
    }
  }

  PVector multi (PVector v, float m)
  {
    return new PVector (v.x*m, v.y*m);
  }


  void display ()
  {
    //strokeWeight (0.5);
    //fill(#b1c999, 255);
     stroke(colour);
     ellipse( location.x, location.y, 4, 4);  // was point
  }
}

color getNextColour() {
    color c; 
    hue += noise(.000011);
    if (hue>360) hue=0;
    c = color(hue, 360, 360);  
   return  c;
}


void keyPressed ()
{
  if (key == 'b') bDisplayVectorField = !bDisplayVectorField;
  if (key == 'p') displayParticle = !displayParticle;
  if (key == '+') m.add (new Mover());
  
  if (key == ' ') noiseSeed ((int) random (10000));
  
  if (key == '-')
  {
    if (m.size() > 0) m.remove (0);
  }
  
}

void displayVectorField()
{
  stroke (#b1c999, 40);
  stroke (247, 20);

  float xSpan = (width - (xRes-1)*(float) padding) / xRes;
  float ySpan = (height - (yRes-1)*(float) padding) / yRes;

  float x, y, angle;
  noFill();

  float dis;
  float mm = map (posX, 0, width, -1.5, 1.5);
  if (mm == 0) mm = 0.0001;
  float maxDis = mm*dist (0, 0, width/2, height/2);
float fAngle;

  for (int i = 0; i < yRes; i++)
  {
    for (int j = 0; j < xRes; j++)
    {

      x = xSpan * j + padding*j + xSpan/2;
      y = ySpan * i + padding*i + ySpan/2;
      dis = dist (x, y, width/2, height/2);
      fAngle = map (dis, 0, maxDis, posY, 0);
      angle = map (dis, 0, maxDis, PI, 0) +  random (-PI/4*fAngle, PI/4*fAngle);

      line (x, y, x+cos (angle) * xSpan, y + sin (angle) * ySpan);
    }
  }
}