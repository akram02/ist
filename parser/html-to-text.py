from bs4 import BeautifulSoup
file = open('computer-7.html') # converted by https://www.pdftohtml.net/
soup = BeautifulSoup(file.read(), 'html.parser')
lines = soup.text
print(lines)
textFile = open("computer-7.txt", "w")
textFile.write(lines)