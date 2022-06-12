import numpy as np
import cv2
import pickle
import mysql.connector

mydb = mysql.connector.connect(
      host="localhost",
      user="root",
      passwd="",
      database="dacs5"
)
cursor = mydb.cursor()

face_cascade = cv2.CascadeClassifier('cascades/data/haarcascade_frontalface_default.xml')
eye_cascade = cv2.CascadeClassifier('cascades/data/haarcascade_eye.xml')
smile_cascade = cv2.CascadeClassifier('cascades/data/haarcascade_smile.xml')
recognizer = cv2.face.LBPHFaceRecognizer_create()
recognizer.read("trainner.yml")

labels = {"person_name": 1}

with open("labels.pickle", 'rb') as f:
        og_labels = pickle.load(f)
        labels = {v:k for k,v in og_labels.items()}

cap = cv2.VideoCapture(0)
print(labels)
while (True):
    # Capture frame-by-frame
    ret, frame = cap.read()
    gray = cv2.cvtColor(frame,cv2.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray,scaleFactor=1.5, minNeighbors=5)
    for (x, y, w,h) in faces:
        #print(x,y,w,h)
        roi_gray = gray[y:y+h, x:x+w]
        roi_color = frame[y:y+h, x:x+w]
        id_, conf = recognizer.predict(roi_gray)
        print( id_)
        if conf >= 4 and conf <= 85: 
            #print(id_)
            #print(labels[id_])
            font = cv2.FONT_HERSHEY_SIMPLEX
            name = labels[id_]
            color = (255, 255, 255)
            stroke = 2
            cv2.putText(frame, name, (x,y), font, 1, color, stroke, cv2.LINE_AA)
            sql = "UPDATE student SET status = 1 WHERE IDStudent = '%s'" % (name)
            print(sql)
            cursor.execute(sql)
            mydb.commit()
            
        img_item = "img1.jpg"
        cv2.imwrite(img_item, roi_gray)
        
        color = (255,0,0)
        stroke = 2
        end_cord_x = x + w
        end_cord_y = y + h
        cv2.rectangle(frame, (x, y), (end_cord_x, end_cord_y), color, stroke)
        subitems = smile_cascade.detectMultiScale(roi_gray)
        for (ex,ey,ew,eh) in subitems:
            cv2.rectangle(roi_color,(ex,ey),(ex+ew,ey+eh),(0,255,0),2)
        
    # Display the resulting frame
    resized_img = cv2.resize(frame, (1000, 700))
    cv2.imshow("Create dataset face", resized_img)
    #cv2.imshow('frame', frame)
    if cv2.waitKey(20) & 0xFF == ord('x'):
        break

# When everythign done, release the capture
cap.release()
cv2.destroyAllWindows()
