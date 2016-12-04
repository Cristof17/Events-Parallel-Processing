Rotsching Cristofor 343C1

Implementarea temei conține următoarele clase:
-Main
-WorkPool -> luată din laboratorul 7
-Worker -> luată din laboratorul 7 și modificată
-Event -> conține un N și tipul evenimentului
-Type -> enum cu tipurile de evenimente
-Results -> agregă un ArrayList<Result>
-Result -> POJO ce descrie un rezultat

În funcția main am făcut urmatorul lucru: 

Creare workpool, de dimensiunea cozii de evenimente (ArrayBlockingQueue) de
dimensiune primită ca argument. 
Instanțierea unui semafor care să permită thread-ului main să scrie în fișiere
odată ce Workerii au terminat de procesat evenimentele. Executtia din thread-ul
main face acquire(nrThreads) pentru a obliga fiecare worker să dea release pe
același aceeași referință. Numărul de permit-uri pentru semaphore este același 
ca numărul de thread-uri, astfel încât dacă se face acquire după pornirea
thread-urilor, trebuie ca toate să termine până când thread-ul main scrie în
fișierele de output. 

Fiecare thread, primește referință către semaphore pentru a da release, atunci 
când termină de procesat evenimente, o referință către un obiect de tip Results,
în care va pune fieacre rezultat procesat. Refeința către obiectul de tip Results,
thread-ul main va itera prin obiect, selectând rezultatele funcție de tipul lor, 
și pasând-ule unui bufferedWriter care va scrie în fișierul corespunzător 
tipului de eveniment. 

Prin faptul că procesarea unui eveniment de tip Fibo era foarte înceată deaorece
era recursivă, am decis să implementez fibo, cu apeluri de funcție recursive, dar 
care țin minte ultimele două rezultate ale funcției.