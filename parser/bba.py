from bs4 import BeautifulSoup

file = open('bba.html') # converted by https://www.pdftohtml.net/
soup = BeautifulSoup(file.read(), 'html.parser')
lines = soup.text

majors = [ "BBA Major in Accounting & Information Systems", "BBA Major in Management Studies", "BBA Major in Marketing", "BBA Major in Finance & Banking"]


lineList = lines.splitlines()
print(lines)
# print(lineList[1000])
for f in lines.splitlines():
    # print(f)
    if f.__contains__("Course"):
        # print(f)
        pass