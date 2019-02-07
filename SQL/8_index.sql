CREATE INDEX Price_bT ON Products (price);

CREATE INDEX nationality_hT USING HASH ON Players (nationality)
