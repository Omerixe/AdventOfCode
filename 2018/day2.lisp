(load "basics.lisp")
;; Day 2
;; Input
(defvar *day2* (string-split #\Newline (file-to-string "day2.txt")))

(defun remove-element (element list)
   (remove-if (lambda (x) (equal x element)) list))

(defun check-char-x-times (input x)
  (cond 
    ((null input) nil)
    ((equal x (count (car input) input)) t)
    (t (check-char-x-times (remove-element (car input) input) x))))

(defun day2-part1 (input)
  (* 
    (count-if (lambda (x) (check-char-x-times x 2)) input) 
    (count-if (lambda (x) (check-char-x-times x 3)) input)))

;;Test
(print "Test 1:")
(print (day2-part1 (mapcar (lambda (x) (coerce x 'list)) '("abcdef" "bababc" "abbcde" "abcccd" "aabcdd" "abcdee" "ababab"))))

(print "Answer 1:")
(print (day2-part1 (mapcar (lambda (x) (coerce x 'list)) *day2*)))


(defun count-diff (list1 list2)
  (cond
    ((null list1) 0)
    ((equal (car list1) (car list2)) (count-diff (cdr list1) (cdr list2)))
    (t (+ 1 (count-diff (cdr list1) (cdr list2))))))

(defun common-elements (list1 list2)
  (cond 
    ((null list1) nil)
    ((equal (car list1) (car list2)) (cons (car list1) (common-elements (cdr list1) (cdr list2))))
    (t (common-elements (cdr list1) (cdr list2)))))

(defun day2-part2 (input)
  (block outer (loop for x in input do
    (loop for y in input do
      (when (equal 1 (count-diff x y)) (return-from outer (coerce (common-elements x y) 'string)))))))

(print "Test 2:")
(print (day2-part2 (mapcar (lambda (x) (coerce x 'list)) '("abcde" "fghij" "klmno" "pqrst" "fguij" "axcye" "wvxyz"))))

(print "Answer 2:")
(print (day2-part2 (mapcar (lambda (x) (coerce x 'list)) *day2*)))

