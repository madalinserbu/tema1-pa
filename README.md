Implementarea clasei MyScanner am luat-o de pe ocw-ul de PA: https://ocw.cs.pub.ro/courses/pa/tutoriale/coding-tips

## Servere
Complexitate: NlogC
logC deoarece se face cautare binara dupa pragul de alimentare (care are valori intre 0 si C), iar pt fiecare valoare
se calculeaza puterea tutoror serverelor (N).

Am folosit cautare binara pentru a cauta o valoare optima a tensiunii de alimentare. Am observat ca orice valoare poate fi incadrata in unul din cazurile:
- tensiunea de alimentare curenta este fix tensiunea de alimentare a serverului cu putere minima (care da puterea totala). In
acest caz, nu se poate face nicio imbunatatire (orice modificare a tensiunii ar reduce si mai mult puterea totala), deci aceasta
este tensiunea cautata
- avem 2 sau mai multe servere cu putere minima, iar cel putin unul din ele este subalimentat, si cel putin unul supraalimentat.Si
in acest caz orice modificare ar duce reduce sigur puterea unuia din serverele cu putere minima, deci si puterea totala, astfel ca 
din nou aceasta este puterea cautata
- serverul cu putere minima este subalimentat. Tensiunea optima este deci mai mare decat cea curenta. Daca am epuizat deja valorile intregi mai mari decat tensiunea curenta, atunci tensiunea cautata este la mijlocul dintre tensiunea curenta si urmatoarea
valoare intreaga (adica la +0.5).
- serverul cu putere minima este supraalimentat, similar cu cazul anterior, doar ca tensiunea optima este mai mica decat cea curenta.
## Colorare
Complexitate: K * logN
Pentru fiecare grup (in total K grupuri), facem o ridicare la putere de exponent maxim N, calculata cu divide et impera, in logN.

Numarul de moduri in care putem aseza dreptungiurile la pasul curent este dat de asezarea la pasul anterior. Numarul total de moduri de asezare este produsul numarului de moduri de asezare pentru fiecare pas.

La pasul initial putem aseza dreptunghiuri orizontale in 6 moduri, verticale in 3. Dupa dreptunghiuri orizontale putem pune unul vertical intr-un singur mod, iar dupa unul vertical putem pune orizontale in 2 moduri.

In fine, putem pune orizontale dupa orizontale in 3 moduri, si verticale dupa verticale in 2 moduri. Acesta este motivul pentru
care am folosit ridicarea la putere a lui 3, respectiv 2, la numarul de elemente din grup - 1. (acel 1 este contorizat la "granita" dintre grupuri).

## Compresie
Complexitate: O(N) - parcurg simultan cei doi vectori, numarul maxim de pasi fiind N + M
Am folosit o abordare greedy, in care pornesc de la inceputul celor doi vectori si daca valorile nu se potrivesc, fac
compresie in vectorul cu valoarea mai mica (sperand sa ajung la valoarea din vectorul celalalt). Repet procedeul pana ajung
sa am o valoare comuna, care va fi in sirul final, si continui apoi cu urmatoarele valori din fiecare vector.
## Criptat
Complexitate: O(N*L) - din constructia matricei dp, care la limita are dimensiunea N*L

In primul rand, dat fiind faptul ca sunt maxim 8 litere, am tratat pe rand cazul in care fiecare litera e considerata dominanta.
Pentru fiecare litera, am impartit cuvintele in 2 categorii:
- cele in care litera e dominanta. Acestea vor fi sigur incluse in parola, deci le contorizam lungimea, si in plus aduc si un
'stoc' de litere dominante, care ne permite sa adaugam apoi si cuvinte cu mai multe litere nedominante. La fel, cuvintele in care
litera apare de la fel de multe ori cu toate celelalte la un loc vor fi incluse, pentru ca nu afecteaza in niciun fel conditia de
dominanta, dar aduc un beneficiu la lungime.
- cele in care litera nu e dominanta. Aceste cuvinte ne pot afecta constrangerea asupra parolei, dar vrem sa le folosim daca se poate
pentru a obtine o parola mai lunga. Pentru asta am folosit programare dinamica, construind o matrice dp in care dp[i][j] reprezinta
lungimea maxima a unei parole folosind primele i cuvinte nedominante si un 'stoc' j de litere dominante (in sensul ca o litera 
dominanta 'consuma' o alta litera).

In final solutia este maxima dintre solutiile obtinute pentru fiecare litera dominanta.

## Oferta
Complexitate: O(N*K) - calculez cele mai bune k preturi pentru primele i produse, cu i pana la N.

Cele mai bune k preturi pentru primele i produse se obtin din cele mai bune k preturi pentru primele i-1, i-2 si i-3 produse (programare dinamica)
Astfel, pentru fiecare i, calculez preturile posibile luand pe rand de la cel mai bun la al k-lea cel mai bun pret pentru i-1, i-2 si i-3 produse, adaugand pretul produsului, pretul produsului la oferta cu precedentul, respectiv la oferta cu ultimele 2. Ma opresc cand am k preturi distincte, pentru ca urmatoarele vor fi sigur mai mari (se trece la un element mai putin bun din unul
din vectorii anteriori). Cele k preturi se genereaza pentru fiecare element in ordine crescatoare, deci nu e nevoie de sortare.
Singurul caz special este cand se obtine un pret duplicat (dar conditia este usor de verificat, deoarece elementele generandu-se in
ordine crescatoare, e necesara doar o comparatie cu ultimul element).

Solutia este ultimul element din cele k ale vectorului de preturi pentru primele N produse.