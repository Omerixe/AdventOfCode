;; Reads a file into a string
(defun file-to-string (path)
  (with-open-file (stream path)
    (let ((data (make-string (file-length stream))))
      (read-sequence data stream)
      data)))

;; Makes a list from the string
(defun string-split (c strg &optional (start 0))
  (let ((end (position c strg :start start))) 
    (cond 
      (end (cons (subseq strg start end) (string-split c (subseq strg (+ end 1))))) 
      (t (list (subseq strg start))) 
    )
  )
)


