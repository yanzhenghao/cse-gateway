package main

import (
	"fmt"
	"io"
	"log"
	"net"
	"net/http"
	"os"
	"os/signal"
	"strings"
	"sync"
	"syscall"
)

const (
	checkHeader  = "x-ext-authz"
	allowedValue = "allow"
	port         = ":8002"
)

type ExtAuthzServer struct {
	httpServer *http.Server
	httpPort   chan int
}

func (s *ExtAuthzServer) ServeHTTP(response http.ResponseWriter, request *http.Request) {
	body, err := io.ReadAll(request.Body)
	if err != nil {
		log.Printf("[HTTP] read body failed: %v", err)
	}

	l := fmt.Sprintf("%s %s%s, headers: %v, body: [%s]\n", request.Method, request.Host, request.URL, request.Header, body)
	if allowedValue == request.Header.Get(checkHeader) && strings.Contains(request.URL.Path, "auth") {
		log.Printf("[HTTP][allowed]: %s", l)
		response.WriteHeader(http.StatusOK)
		return
	}

	log.Printf("[HTTP][denied]: %s", l)
	response.WriteHeader(http.StatusForbidden)
	_, _ = response.Write([]byte("[HTTP][denied]: missing header 'x-ext-authz: allow' in the request"))

}

func (s *ExtAuthzServer) startHTTP(address string, wg *sync.WaitGroup) {
	s.httpServer = &http.Server{Handler: s}
	s.httpPort = make(chan int, 1)

	defer func() {
		wg.Done()
		log.Printf("Stopped HTTP server")
	}()

	listener, err := net.Listen("tcp", address)
	if err != nil {
		log.Fatalf("Failed to create HTTP server: %v", err)
	}
	// Store the port for test only.
	s.httpPort <- listener.Addr().(*net.TCPAddr).Port

	log.Printf("Starting HTTP server at %s", listener.Addr())
	if err := s.httpServer.Serve(listener); err != nil {
		log.Fatalf("Failed to start HTTP server: %v", err)
	}
}

func main() {
	go startHttp()
	sigs := make(chan os.Signal, 1)
	signal.Notify(sigs, syscall.SIGINT, syscall.SIGTERM)
	<-sigs
}

func startHttp() {
	var wg sync.WaitGroup
	wg.Add(1)
	var e ExtAuthzServer
	go e.startHTTP(port, &wg)
	wg.Wait()
}
