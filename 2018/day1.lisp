(load "basics.lisp")
;; Day 1
;; Input
(defvar *day1* (string-split #\Newline (file-to-string "day1.txt")))

;; Part 1
(print "Answer 1:")
(print (apply #'+ (mapcar #'parse-integer *day1*)))

;; Part 2
(defun sum-list-elements (input &optional (initial 0))
  (cond ((null input) nil)
  	(t (let ((res (+ initial (car input))))
  	   (cons res
  	   (sum-list-elements (cdr input) res))))))

(defun day1-part2 (input &optional (results nil) (initial 0))
  (let 
    ((newlist (sum-list-elements input initial)))
    (cond 
      ((null (intersection results newlist)) (day1-part2 input (union results newlist) (car(last newlist))))
      (t (first-occ newlist results)))))

(defun first-occ (list1 list2)
  (loop for x in list1 do
    (cond 
      ((not (null (member x list2))) (return x)))))

(print "Answer 2 (can take a while):")
(print (day1-part2 (mapcar #'parse-integer *day1*)))
