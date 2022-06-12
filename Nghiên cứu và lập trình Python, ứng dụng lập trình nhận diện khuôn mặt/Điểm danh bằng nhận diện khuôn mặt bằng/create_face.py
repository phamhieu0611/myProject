import cv2
import os
import mysql.connector

mydb = mysql.connector.connect(
     host="localhost",
      user="root",
      passwd="",
      database="dacs5"
)
cursor = mydb.cursor()

cam = cv2.VideoCapture(0)
facecascade = cv2.CascadeClassifier("cascades/data/haarcascade_frontalface_default.xml")
i = 0
j = 0
offset = 50
name = input("Nhập Tên Sinh Viên:")
idstudent = input("Nhập Mã Sinh Viên:")

#creat folder in images folder
path = "images/"+idstudent
if os.path.exists(path):
	print("Folder is exists")
else:
    print("Folder is created")
    os.mkdir(path)
print (path)
#count image sample in folder
for root,dirs,files in os.walk(path):
   for file in files:
   	   j += 1	   
while True:
	ret,img = cam.read()
	gray = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
	faces = facecascade.detectMultiScale(gray,1.3,5)
	for (x,y,w,h) in faces:
		i += 1
		cv2.imwrite("images/"+idstudent+".jpg", img)
		cv2.imwrite(path+"/"+idstudent+"_"+str(i+j)+".jpg",gray[y-offset:y+h+offset,x-offset:x+w+offset])
		cv2.rectangle(img,(x,y),(x+w,y+h),(225,0,0),1)
		resized_img = cv2.resize(img, (1000, 700))
		cv2.imshow("Create dataset face", resized_img)
		#cv2.imshow("Create dataset face",img)
		cv2.waitKey(100)
	print(i)
	if i >= 100:
		cam.release()
		cv2.destroyAllWindows()
		break


sql = "INSERT INTO student(idstudent,name,img) Values('%s', '%s', '%s')" % (idstudent, name, "images/"+idstudent+'.jpg')
print(sql)
cursor.execute(sql)
mydb.commit()
