require 'io/console'
STDIN.echo = false
STDIN.raw!
buffer = ""
begin
  buffer << STDIN.read_nonblock(1)
rescue
  retry
end
STDIN.echo = true
STDIN.cooked!

puts buffer
