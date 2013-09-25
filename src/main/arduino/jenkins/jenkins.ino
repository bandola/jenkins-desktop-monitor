#include "pitches.h"
int GAMEOVER_MELODY[] = {
  NOTE_C4, NOTE_G3,NOTE_G3, NOTE_A3, NOTE_G3,0, NOTE_B3, NOTE_C4
};

int GAMEOVER_TEMPO[] = {
  4,8,8,4,4,4,4,4
};

int MARIO_MELODY[] = {
  NOTE_C4, NOTE_C5, NOTE_A3, NOTE_A4, 
  NOTE_AS3, NOTE_AS4, 0,
  0,
  NOTE_C4, NOTE_C5, NOTE_A3, NOTE_A4, 
  NOTE_AS3, NOTE_AS4, 0,
  0,
  NOTE_F3, NOTE_F4, NOTE_D3, NOTE_D4,
  NOTE_DS3, NOTE_DS4, 0,
  0,
  NOTE_F3, NOTE_F4, NOTE_D3, NOTE_D4,
  NOTE_DS3, NOTE_DS4, 0,
  0, NOTE_DS4, NOTE_CS4, NOTE_D4,
  NOTE_CS4, NOTE_DS4, 
  NOTE_DS4, NOTE_GS3,
  NOTE_G3, NOTE_CS4,
  NOTE_C4, NOTE_FS4,NOTE_F4, NOTE_E3, NOTE_AS4, NOTE_A4,
  NOTE_GS4, NOTE_DS4, NOTE_B3,
  NOTE_AS3, NOTE_A3, NOTE_GS3,
  0, 0, 0
};

int MARIO_TEMPO[] = {
  12, 12, 12, 12, 
  12, 12, 6,
  3,
  12, 12, 12, 12, 
  12, 12, 6,
  3,
  12, 12, 12, 12, 
  12, 12, 6,
  3,
  12, 12, 12, 12, 
  12, 12, 6,
  6, 18, 18, 18,
  6, 6,
  6, 6,
  6, 6,
  18, 18, 18,18, 18, 18,
  10, 10, 10,
  10, 10, 10,
  3, 3, 3
};

int red = 13;
int green = 12;
int yellow = 11;
int buzzer = 10;

int currentLed = -1;
boolean blinking = false;

void setup() {               
  Serial.begin(9600); 
  pinMode(red, OUTPUT);
  pinMode(green, OUTPUT);
  pinMode(yellow, OUTPUT);
  pinMode(buzzer, OUTPUT);
}

void loop() {
  processSerialInput();
  
  if(blinking == true){
    blinkCurrentLed();
  }
}

void processSerialInput(){
   if (Serial.available() > 0) {
    int inByte = Serial.read();

    switch (inByte) {
    case 'r':    
      turnOnSingleLed(red);
      playImperialMarch();
      break;
    case 'g':    
      turnOnSingleLed(green);
      break;
    case 'y':    
      turnOnSingleLed(yellow);
      break;
    case 'b':    
      blinking = true;
      break;
    case 'o':
      turnOffAllLeds();
      Serial.write("k");         
    } 
  }
}

void turnOffAllLeds(){
  for(int i = 11; i <= 13; i++){
    digitalWrite(i, LOW);
  } 
}

void blinkCurrentLed(){
  digitalWrite(currentLed, LOW);
  delay(1000);
  digitalWrite(currentLed, HIGH);
  delay(1000);
}
void turnOnSingleLed(int led){
  turnOffAllLeds();
  blinking = false;
  currentLed = led;
  digitalWrite(led, HIGH);
}

void playGameOverSong(){
  playSong(GAMEOVER_MELODY, GAMEOVER_TEMPO, sizeof(GAMEOVER_MELODY)); 
}

void playMarioMainTheme(){
  playSong(MARIO_MELODY, MARIO_TEMPO, sizeof(MARIO_MELODY)); 
}

void beep (int frequencyInHertz, long timeInMilliseconds)
{ 
   
    int x; 	 
    long delayAmount = (long)(1000000/frequencyInHertz);
    long loopTime = (long)((timeInMilliseconds*1000)/(delayAmount*2));
    for (x=0;x<loopTime;x++) 	 
    { 	 
        digitalWrite(buzzer,HIGH);
        delayMicroseconds(delayAmount);
        digitalWrite(buzzer,LOW);
        delayMicroseconds(delayAmount);
    } 	 
    
    
    delay(20);
    //a little delay to make all notes sound separate
} 	 
  	 
void playImperialMarch()
{ 	 
   //Adaptado de https://gist.github.com/tagliati/1804108
    beep(NOTE_A4, 500); 
    beep(NOTE_A4, 500);     
    beep(NOTE_A4, 500); 
    beep(NOTE_F4, 350); 
    beep(NOTE_C5, 150);
    
    beep(NOTE_A4, 500);
    beep(NOTE_F4, 350);
    beep(NOTE_C5, 150);
    beep(NOTE_A4, 1000);
       
    beep(NOTE_E5, 500);
    beep(NOTE_E5, 500);
    beep(NOTE_E5, 500);    
    beep(NOTE_F5, 350); 
    beep(NOTE_C5, 150);
    
    beep(NOTE_GS4, 500);
    beep(NOTE_F4, 350);
    beep(NOTE_C5, 150);
    beep(NOTE_A4, 1000);
   
}

void playSong(int melody[], int tempo[], int size){
  int newSize = size / sizeof(int);
  for (int thisNote = 0; thisNote < newSize; thisNote++) {
    int noteDuration = 1000/tempo[thisNote];
    tone(10, melody[thisNote],noteDuration);
    int pauseBetweenNotes = noteDuration * 1.30;
    delay(pauseBetweenNotes);
    noTone(10);
  }
}


