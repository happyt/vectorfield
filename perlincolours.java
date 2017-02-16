function setup() {
  createCanvas(windowWidth, windowHeight); 
  background(100);  
  hue=300;
  hue2=0;
  colorMode(HSB, 360);
  noStroke();
  
  for (var i=0; i<800; i++) {
    hue += 0.5;
    if (hue>360) hue=0;
  	fill(hue, 360,360);  
   	rect(i*1, 80, 1, 10); 
  }
  
  for (var i=0; i<400; i++) {
      hue += noise(.0015);
      if (hue>360) hue=0;
  	  fill(hue, 360, 360);  
      rect(i*2, 100, 2, 10);
  }
 
 for (var i=0; i<400; i++) { 
    hue2 = hue;
    for (var j=0; j<100; j++) {
      hue += noise(.15 * j  * i);
      if (hue>360) hue=0;
  	  fill(hue, 360, 360);  
      rect(i*2, 120+j*3, 2, 3); 
    }
    hue = hue2 + noise(.15 * j  * i);;
  }
}

function draw() {
//  ellipse(mouseX, mouseY, 20, 20);
}